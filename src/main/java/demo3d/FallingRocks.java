package demo3d;

import java.io.Serial;
import java.util.Set;

import demo3d.models.Grid;
import rcs.mulder.color.MulderColor;
import rcs.mulder.gui.MulderFrame;
import rcs.mulder.math.MathConsts;
import rcs.mulder.math.MathUtils;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.entities.Rotation3d;
import rcs.mulder.three.entities.models.Model3d;
import rcs.mulder.three.entities.models.Model3dFace;
import rcs.mulder.three.entities.models.Model3dFactory;
import rcs.mulder.three.entities.models.Model3dUtils;
import rcs.mulder.three.gfx.TextureRaster;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.optics.AmbientLightSource3d;
import rcs.mulder.three.optics.ConstantLightSource3d;
import rcs.mulder.utils.AnimationTimer;
import rcs.mulder.utils.XORShift;
import rcs.mulder.utils.struct.MulderCollection;
import rcs.mulder.utils.struct.MulderLinkedList;

import static rcs.mulder.three.render.RenderOptions3d.Option.*;

public class FallingRocks extends Demo3d {

  @Serial
  private static final long serialVersionUID = 1L; 
  
  private final double rockRadius = 0.5;

  private final TextureRaster rockTexture = Model3dUtils.getImageData(System.getProperty("user.dir") + "/textures/texture2.jpg");
  
  private final XORShift xorShift = XORShift.getInstance();
  
  private final Grid ground = new Grid(10, 10, 10);
  
  private final MulderCollection<Model3d> rocks = new MulderLinkedList<>();
  private final MulderCollection<Model3d> shards = new MulderLinkedList<>();
  
  private final AnimationTimer addRockTimer = new AnimationTimer(this::addNewRock, 1000);
  
  public FallingRocks() {
    super();
  }
  
  @Override
  public void initialize() {
    super.initialize();
    
    super.setBackgroundColor(MulderColor.white);
    
    camera.translate(0, 4, 10);
    
    ground.setColorToAllFaces(MulderColor.darkOliveGreen);

    Model3dUtils.setOptions(
        ground, 
        Set.of(meshOnly), 
        Set.of());
    
    MulderRuntime.getRepository().add(ground);
    MulderRuntime.getRepository().add(rocks);
    MulderRuntime.getRepository().add(shards);
    
    var lightSource = new ConstantLightSource3d(1);
    lightSource.setPosition(5, 5, 5);
    MulderRuntime.addDiffuseLightSource(lightSource);
    MulderRuntime.setAmbientLight(new AmbientLightSource3d(1));
  }

  @Override
  public void pausedLoop() { 
    controlCamera();
  }
  
  @Override
  public void runningLoop() { 
    controlCamera();
    addRockTimer.run();
    animateRocks();
    animateShards();
  }
  
  private void animateRocks() {
    rocks.forEach(rock -> {
      rock.spin(Vector3d.Z_AXIS, 0.01);
      if (rock.getPosY() - rockRadius < 0) {
        rock.destroy();
        rock.setPosition(rock.getPosX(), rockRadius + 0.1, rock.getPosZ());
        addNewShards(rock);
      }
      rock.animate();
    });
  }
  
  private void animateShards() {
    shards.forEach(shard -> {
      if (shard.getVelocity().equals(Vector3d.ZERO)) {
        for (Model3dFace face : shard.getFaces()) {
          int newAlpha = MathUtils.roundToInt(face.getAlpha() - 1);
          face.setAlpha(newAlpha);
          if (newAlpha < 5) {
            shard.destroy();
          }
        }
      } else {
        if (shard.getPosition().y() < 0.05) {
          shard.setPosY(0.04);
          shard.setVelocity(0, 0, 0);
        } else { 
          shard.accelerate(0, -0.01, 0);
          shard.animate();
        }
      }
    });
  }
  
  private void addNewRock() {
    var rock = Model3dFactory.dodecahedron(rockRadius)
        .setTextureData(rockTexture)
        .setPosition(new Vector3d(xorShift.randomDouble(-5, 5), 15, xorShift.randomDouble(-5, 5)))
        .build();
    
    Model3dUtils.setOptions(
        rock, 
        Set.of(flatShaded), 
        Set.of(gouraudShaded));
    
    Model3dUtils.deform(rock, 0.1);

    rock.setVelocity(0, -0.2, 0);
    rocks.add(rock);
  }
  
  private void addNewShards(Model3d rock) {
    var newShards = Model3dUtils.partition3d(rock);
    for (var shard : newShards) {
      Model3dUtils.setOptions(
          shard, 
          Set.of(flatShaded), 
          Set.of(gouraudShaded));
      
      Vector3d velocity = Vector3d.fromSpherical(
          0.15,
          xorShift.randomDouble(0, MathConsts.PI),
          xorShift.randomDouble(MathConsts.QUARTER_PI, MathConsts.THREE_QUARTER_PI));
      
      shard.setVelocity(velocity);
      
      Vector3d axis = velocity.crossProd(Vector3d.Y_AXIS);
      
      Rotation3d rotation = Rotation3d.spin(axis, -0.1);
      shard.setRotation(rotation);
      
      shards.add(shard);
    }
  }

  public static void main(String[] args) {
    var frame = new MulderFrame(800, 800, "Rain Demo", true, false);
    var demo = new FallingRocks();
    frame.add("Center", demo);
    frame.setVisible(true);
    demo.init();
  }
}
