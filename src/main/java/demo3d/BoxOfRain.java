package demo3d;

import java.io.Serial;

import demo3d.models.Grid;
import rcs.mulder.color.MulderColor;
import rcs.mulder.gui.MulderFrame;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.entities.models.Model3d;
import rcs.mulder.three.entities.models.Model3dFactory;
import rcs.mulder.three.entities.primitives.Line3d;
import rcs.mulder.three.entities.primitives.Polygon3d;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.utils.XORShift;
import rcs.mulder.utils.struct.MulderCollection;
import rcs.mulder.utils.struct.MulderLinkedList;

import static rcs.mulder.three.render.RenderOptions3d.Option.*;

public class BoxOfRain extends Demo3d {

  @Serial
  private static final long serialVersionUID = 1L;
  
  private final XORShift xorShift = XORShift.getInstance();
  
  private final Grid ground = new Grid(10, 10);
  
  private final MulderCollection<Line3d> raindrops = new MulderLinkedList<>();
  private final MulderCollection<Polygon3d> waves = new MulderLinkedList<>();
  private final MulderCollection<Model3d> splashes = new MulderLinkedList<>();
  
  public BoxOfRain() {
    super();
  }
  
  @Override
  public void initialize() {
    super.initialize();
    
    super.setBackgroundColor(MulderColor.black);
    
    camera.translate(0, 4, 10);
    
    ground.setColorToAllFaces(MulderColor.blueViolet);
    
    MulderRuntime.getRepository().add(ground);
    MulderRuntime.getRepository().add(raindrops);
    MulderRuntime.getRepository().add(waves);
    MulderRuntime.getRepository().add(splashes);
  }

  @Override
  public void pausedLoop() { 
    controlCamera();
  }
  
  @Override
  public void runningLoop() { 
    controlCamera();

    addNewRaindrops();
    animateRaindrops();
    animateWaves();
    animateSplashes();
  } 
  
  private void addNewRaindrops() {
    for (int i = 0; i < 1; i++) {
      var position = new Vector3d(xorShift.randomDouble(-5, 5), 10, xorShift.randomDouble(-5, 5));
      var raindrop = new Line3d(position, position.add(0, 0.1, 0));
      raindrop.setColor(MulderColor.white);
      raindrop.setVelocity(0, -0.1, 0);
      raindrops.add(raindrop);
    }
  }
  
  private void animateRaindrops() {
    raindrops.forEach(raindrop -> {
      raindrop.animate();
      if (raindrop.getA().y() < 0) {
        raindrop.destroy();
        addNewWave(raindrop);
        addNewSplash(raindrop);
      }
    });
  }
  
  private void animateWaves() {
    double maxRadius = 2;
    waves.forEach(wave -> {
      double radius = wave.getVertices()[0].distance(wave.getCenterOfMass());
      wave.scale(1 + 0.005/(radius / maxRadius));
      wave.setColor(wave.getColor().fadeTo(1 - (radius / maxRadius)));
      if (radius > maxRadius) {
        wave.destroy();
      }
    });
  }
  
  private void addNewWave(Line3d raindrop) {
    var wave = Polygon3d.regularPolygon(0.1, 10);
    wave.setPosition(raindrop.getA().x(), 0.01, raindrop.getA().z());
    wave.setColor(MulderColor.white.fadeTo(0.9));
    wave.getRenderOptions().enable(meshOnly);
    waves.add(wave);
  }
  
  private void addNewSplash(Line3d raindrop) {
    for (int i = 0; i < 5; i++) {
      var position = new Vector3d(raindrop.getA().x(), 0.01, raindrop.getA().z());
      var velocity = new Vector3d(
          xorShift.randomDouble(-0.02, 0.02),
          0.1, 
          xorShift.randomDouble(-0.02, 0.02));
      
      var splash = Model3dFactory.cube(0.03)
          .setPosition(position)
          .setVelocity(velocity)
          .setColor(MulderColor.white)
          .build();
      
      splashes.add(splash);
    }
  }
  
  private void animateSplashes() {
    splashes.forEach(splash -> {
      splash.accelerate(0, -0.01, 0);
      splash.animate();
      if (ground.pointIsBehind(splash.getCenter())) {
        splash.destroy();
      }
    });
  }

  public static void main(String[] args) {
    var frame = new MulderFrame(800, 800, "Rain Demo", true, false);
    var demo = new BoxOfRain();
    frame.add("Center", demo);
    frame.setVisible(true);
    demo.init();
  }
}