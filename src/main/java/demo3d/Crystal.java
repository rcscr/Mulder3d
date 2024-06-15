package demo3d;

import java.io.Serial;
import java.util.Set;

import rcs.mulder.color.MulderColor;
import rcs.mulder.gui.MulderFrame;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.entities.models.Model3d;
import rcs.mulder.three.entities.models.Model3dFactory;
import rcs.mulder.three.entities.models.Model3dUtils;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.optics.AmbientLightSource3d;
import rcs.mulder.three.optics.ConstantLightSource3d;

import static rcs.mulder.three.render.RenderOptions3d.Option.*;

public class Crystal extends Demo3d {

  @Serial
  private static final long serialVersionUID = 1L;

  private final Model3d crystal = Model3dFactory
      .icosphere(1, 2)
      .setTextureData(
          Model3dUtils.getImageData(System.getProperty("user.dir") + "/textures/crystaltexture.jpg"),
          255,
          2.5)
      .build();
  
  @Override
  protected void initialize() {
    super.initialize(); 
    
    setBackgroundColor(MulderColor.black);
    
    Model3dUtils.setOptions(
        crystal, 
        Set.of(gouraudShaded), 
        Set.of());

    
    Model3dUtils.normalizeFacesToTriangles(crystal);
    
    MulderRuntime.getRepository().add(crystal);

    camera.translate(0, 0, 3);
    
    MulderRuntime.addDiffuseLightSource(new ConstantLightSource3d(1));
    MulderRuntime.setAmbientLight(new AmbientLightSource3d(0.5));

    MulderRuntime.getDiffuseLightSources()[0].setPosition(camera.getPosition());
    wzc.setAmount(0.2);
  }

  @Override
  public void pausedLoop() { 
    controlCamera();
  }
  
  @Override
  public void runningLoop() {
    controlCamera();
    crystal.rotate(Vector3d.Y_AXIS, 0.005);         
    Model3dUtils.deform(crystal, 0.005);
  }

  public static void main(String[] args) {
    var frame = new MulderFrame(800, 800, "Crystal Demo", true, false);
    var demo = new Crystal();
    frame.add("Center", demo);
    frame.setVisible(true);
    demo.init();
  }
}