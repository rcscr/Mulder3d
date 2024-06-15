package demo3d;

import java.io.Serial;

import rcs.mulder.color.MulderColor;
import rcs.mulder.gui.MulderFrame;
import rcs.mulder.three.entities.models.Model3d;
import rcs.mulder.three.entities.models.Model3dFactory;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.optics.AmbientLightSource3d;
import rcs.mulder.three.optics.ConstantLightSource3d;

public class Torus extends Demo3d {

  @Serial
  private static final long serialVersionUID = 1L; 
  
  private final Model3d torus = Model3dFactory
      .torus(1, 2, 15, 15)
      .build();

  @Override
  protected void initialize() {
    super.initialize();
    
    setBackgroundColor(MulderColor.black);
    
    MulderRuntime.getRepository().add(torus);
    
    camera.setPosition(torus.getPosition());
    camera.translate(0, 0, 4);
    
    MulderRuntime.setAmbientLight(new AmbientLightSource3d(0.2));
    MulderRuntime.addDiffuseLightSource(new ConstantLightSource3d(1));
    MulderRuntime.getDiffuseLightSources()[0].setPosition(0, 0, 4);
  }
  
  @Override
  public void runningLoop() {
    controlCamera();  
  }

  public static void main(String[] args) {
    var frame = new MulderFrame(600, 600, "Torus Demo", true, false);
    var demo = new Torus();
    frame.add("Center", demo);
    frame.setVisible(true); 
    frame.setLocationRelativeTo(null);
    demo.init();
  }
}
