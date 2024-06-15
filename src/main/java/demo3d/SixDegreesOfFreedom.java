package demo3d;

import java.awt.event.KeyEvent;
import java.io.Serial;

import demo3d.models.Grid;
import rcs.mulder.color.MulderColor;
import rcs.mulder.gui.MulderFrame;
import rcs.mulder.three.entities.models.Model3d;
import rcs.mulder.three.entities.models.Model3dFace;
import rcs.mulder.three.entities.models.Model3dFactory;
import rcs.mulder.three.entities.primitives.Line3d;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.optics.AmbientLightSource3d;
import rcs.mulder.three.optics.ConstantLightSource3d;
import rcs.mulder.math.MathConsts;
import rcs.mulder.math.Matrices;
import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;

import static rcs.mulder.three.render.RenderOptions3d.Option.*;

public class SixDegreesOfFreedom extends Demo3d {

  @Serial
  private static final long serialVersionUID = 1L; 
  
  private final Model3d obj = Model3dFactory.dodecahedron(0.25).setColor(MulderColor.white).build();
  
  private final Line3d up = new Line3d(Vector3d.ZERO, obj.getUpVector());
  private final Line3d sd = new Line3d(Vector3d.ZERO, obj.getSideVector());
  private final Line3d fw = new Line3d(Vector3d.ZERO, obj.getForwardVector());
  
  private final Line3d x = new Line3d(Vector3d.NEG_X_AXIS, Vector3d.X_AXIS);
  private final Line3d y = new Line3d(Vector3d.NEG_Y_AXIS, Vector3d.Y_AXIS);
  private final Line3d z = new Line3d(Vector3d.NEG_Z_AXIS, Vector3d.Z_AXIS);

  private final Grid xyPlane = new Grid(2, 2, 2);
  private final Grid xzPlane = new Grid(2, 2, 2);
  private final Grid zyPlane = new Grid(2, 2, 2);

  private final double angularVelocity = 2 * MathConsts.DEGREES_TO_RADIANS;

  private final Matrix44 xTransform = Matrices.create3dRotateMatrix(Vector3d.ZERO, Vector3d.X_AXIS, angularVelocity);
  private final Matrix44 yTransform = Matrices.create3dRotateMatrix(Vector3d.ZERO, Vector3d.Y_AXIS, angularVelocity);
  private final Matrix44 zTransform = Matrices.create3dRotateMatrix(Vector3d.ZERO, Vector3d.Z_AXIS, angularVelocity);
   
  private ConstantLightSource3d lightSource; 
  
  public SixDegreesOfFreedom() {
    super();
  }
  
  @Override
  public void initialize() {
    super.initialize();
    
    super.setBackgroundColor(MulderColor.bisque4);
    
    obj.translate(0.5, 0.5, 0.5);
    up.translate(obj.getPosition()); 
    sd.translate(obj.getPosition()); 
    fw.translate(obj.getPosition()); 
    
    xyPlane.rotate(Vector3d.X_AXIS, 90 * MathConsts.DEGREES_TO_RADIANS);
    zyPlane.rotate(Vector3d.Z_AXIS, 90 * MathConsts.DEGREES_TO_RADIANS);
    
    xyPlane.setColorToAllFaces(new MulderColor(255, 255, 0, 50));
    xzPlane.setColorToAllFaces(new MulderColor(255, 0, 255, 50));
    zyPlane.setColorToAllFaces(new MulderColor(0, 255, 255, 50));

    x.setColor(MulderColor.red);
    y.setColor(MulderColor.green);
    z.setColor(MulderColor.blue);
    
    obj.setColorToAllFaces(MulderColor.white);
    for (Model3dFace face : obj.getFaces()) {
      face.getRenderOptions().enable(cullIfBackface);
      face.getRenderOptions().enable(gouraudShaded);
    }
    
    up.setColor(MulderColor.green);
    sd.setColor(MulderColor.red);
    fw.setColor(MulderColor.blue);

    MulderRuntime.getRepository().add(obj);
    MulderRuntime.getRepository().add(xyPlane);
    MulderRuntime.getRepository().add(xzPlane);
    MulderRuntime.getRepository().add(zyPlane);
    MulderRuntime.getRepository().add(x);
    MulderRuntime.getRepository().add(y);
    MulderRuntime.getRepository().add(z);
    MulderRuntime.getRepository().add(up);
    MulderRuntime.getRepository().add(sd);
    MulderRuntime.getRepository().add(fw);

    camera.translate(0, 0, 2.5);
    
    lightSource = new ConstantLightSource3d(1);  
    MulderRuntime.addDiffuseLightSource(lightSource);
    MulderRuntime.setAmbientLight(new AmbientLightSource3d(0.2));
    
    wzc.setAmount(0.2);
  }

  @Override
  public void pausedLoop() { 
    controlCamera();
  }
  
  @Override
  public void runningLoop() { 
    controlCamera();
    
    lightSource.setPosition(camera.getPosition());
    
    Matrix44 transform = new Matrix44(); 
    Vector3d position  = obj.getPosition();
     
    if (keyHasBeenPressed(KeyEvent.VK_X)) {
      if (keyHasBeenPressed(KeyEvent.VK_SHIFT)) {
        transform.mulLocal(Matrices.create3dRotateMatrix(position, obj.getSideVector(), angularVelocity)); 
      } else {
        transform.mulLocal(xTransform); 
      }
    }

    if (keyHasBeenPressed(KeyEvent.VK_Y)) {
      if (keyHasBeenPressed(KeyEvent.VK_SHIFT)) {
        transform.mulLocal(Matrices.create3dRotateMatrix(position, obj.getUpVector(), angularVelocity)); 
      } else {
        transform.mulLocal(yTransform); 
      }
    }

    if (keyHasBeenPressed(KeyEvent.VK_Z)) {
      if (keyHasBeenPressed(KeyEvent.VK_SHIFT)) {
        transform.mulLocal(Matrices.create3dRotateMatrix(position, obj.getForwardVector(), angularVelocity)); 
      } else {
        transform.mulLocal(zTransform); 
      }
    }  
        
    obj.transform(transform);
    up.transform(transform);
    sd.transform(transform);
    fw.transform(transform);
  } 

  public static void main(String[] args) {
    var frame = new MulderFrame(800, 800, "Rotation Demo", true, false);
    var demo = new SixDegreesOfFreedom();
    frame.add("Center", demo);
    frame.setVisible(true);
    demo.init();
  }
}
