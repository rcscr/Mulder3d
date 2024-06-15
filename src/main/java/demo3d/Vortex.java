package demo3d;

import rcs.mulder.color.MulderColor;
import rcs.mulder.gui.MulderFrame;
import rcs.mulder.three.anim.Curve3d;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.math.TrigLookUp;
import rcs.mulder.math.Vector3d;

import static rcs.mulder.math.MathConsts.PI;
import static rcs.mulder.math.MathConsts.HALF_PI;
import static rcs.mulder.math.MathConsts.QUARTER_PI;

import java.io.Serial;

public class Vortex extends Demo3d { 

  @Serial
  private static final long serialVersionUID = 1L; 
  
  private final Curve3d curve = new Curve3d(
      (r, t) -> { 
        r = 2*(r-0.5);
        double radius = Math.sqrt(1 - r*r*r);
        return new Vector3d(-radius*TrigLookUp.cos(r*t*PI + t),
                            -radius*TrigLookUp.sin(r*t*PI + t),
                            -r); 
      },
      (r, t, z) -> {         
        return new MulderColor((int)(TrigLookUp.cos(r*t*PI + t)*255),
                             (int)(TrigLookUp.cos(r*t*HALF_PI + t)*255), 
                             (int)(TrigLookUp.cos(r*t*QUARTER_PI + t)*255), 
                             255);
      },
      0.01, 
      0.05);

  public Vortex() { }
  
  @Override
  public void initialize() {
    super.initialize();
    
    wzc.setAmount(0.2);
    
    MulderRuntime.getRepository().add(curve);
    
    camera.translate(0, 0, 3);
  }
  
  @Override
  public void pausedLoop() { 
    controlCamera(); 
  } 
  
  @Override
  public void runningLoop() { 
    controlCamera(); 
    curve.tick();
  }

  public static void main(String[] args) {
    var frame = new MulderFrame(800, 800, "Vortex", true, false);
    var demo = new Vortex(); 
    frame.add("Center", demo);
    frame.setVisible(true);
    demo.init();
  }
}
