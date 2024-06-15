package demo3d;

import java.io.Serial;

import demo3d.models.Grid;
import demo3d.models.PineTree;
import rcs.mulder.color.MulderColor;
import rcs.mulder.gui.MulderFrame;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.entities.models.Model3dUtils;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.optics.AmbientLightSource3d;
import rcs.mulder.three.optics.ConstantLightSource3d;
import rcs.mulder.utils.XORShift;

public class PineForest extends Demo3d {

  @Serial
  private static final long serialVersionUID = 1L;
  
  private final Grid ground = new Grid(
      10, 10, 1,
      Model3dUtils.getImageData(System.getProperty("user.dir") + "/textures/forest-ground.jpg"));

  private final PineTree[] trees = new PineTree[10];
  
  @Override
  protected void initialize() {
    super.initialize(); 
    wzc.setAmount(0.2);
    setBackgroundColor(MulderColor.skyBlue);
    
    MulderRuntime.getRepository().add(ground);
    
    for (int i = 0; i < trees.length; i++) {
      Vector3d position = new Vector3d(
          XORShift.getInstance().randomInt(-4, 4),
          0,
          XORShift.getInstance().randomInt(-4, 4));
      PineTree tree = new PineTree(position, XORShift.getInstance().randomInt(5, 12));
      trees[i] = tree;
      MulderRuntime.getRepository().add(trees[i]);
    }

    camera.translate(0, 4, 10);

    MulderRuntime.setAmbientLight(new AmbientLightSource3d(0.8));
    MulderRuntime.addDiffuseLightSource(new ConstantLightSource3d(1));
    MulderRuntime.getDiffuseLightSources()[0].setPosition(new Vector3d(5, 10, 5));
  }

  @Override
  public void pausedLoop() { 
    controlCamera();
  }
  
  @Override
  public void runningLoop() {
    controlCamera();
  }

  public static void main(String[] args) {
    var frame = new MulderFrame(800, 800, "Pine Demo", true, false);
    var demo = new PineForest();
    frame.add("Center", demo);
    frame.setVisible(true);
    demo.init();
  }
}