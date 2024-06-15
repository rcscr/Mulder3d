package demo3d;

import java.awt.event.KeyEvent;
import java.io.Serial;
import java.util.Set;

import rcs.mulder.color.MulderColor;
import rcs.mulder.gui.MulderFrame;
import rcs.mulder.three.entities.models.Model3d;
import rcs.mulder.three.entities.models.Model3dBuilder;
import rcs.mulder.three.entities.models.Model3dFace;
import rcs.mulder.three.entities.models.Model3dTexturedFace;
import rcs.mulder.three.entities.models.Model3dUtils;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.optics.AmbientLightSource3d;
import rcs.mulder.three.optics.VariableIntensityLightSource3d;
import rcs.mulder.math.MathConsts;
import rcs.mulder.math.Matrices;
import rcs.mulder.math.Vector3d;

import static rcs.mulder.three.render.RenderOptions3d.Option.*;

public class Teapot extends Demo3d { 

  @Serial
  private static final long serialVersionUID = 1;
  
  private final String teapotObjFilePath = System.getProperty("user.dir") + "/objfiles/teapot.obj";

  private final Model3d teapot = new Model3dBuilder()
      .fromObjFile(teapotObjFilePath)
      .setTextureData(Model3dUtils.getImageData(
          System.getProperty("user.dir") + "/textures/tile.jpg"),
          255, 
          1)
      .addTransform(Matrices.create3dScaleMatrix(0.05))
      .build();
  
  public Teapot() { }

  @Override
  protected void initialize() {
    super.initialize();
    
    wzc.setAmount(0.1);
    
    setBackgroundColor(MulderColor.white);
    
    Model3dUtils.setOptions(
        teapot, 
        Set.of(),
        Set.of(cullIfBackface, applyLightingColor, flatShaded, gouraudShaded, textured, bothSidesShaded));
    
    MulderRuntime.getRepository().add(teapot);
    
    camera.setPosition(teapot.getCenter());
    camera.translate(0, 2, 7);
    camera.rotate(Vector3d.X_AXIS, -20 * MathConsts.DEGREES_TO_RADIANS); 

    var lightSourceRed = new VariableIntensityLightSource3d(10, new MulderColor(255, 0, 0));
    var lightSourceGreen = new VariableIntensityLightSource3d(10, new MulderColor(0, 255, 0));
    lightSourceRed.setPosition(3, 4, 5);
    lightSourceGreen.setPosition(-3, 4, 5);
    MulderRuntime.addDiffuseLightSource(lightSourceRed);
    MulderRuntime.addDiffuseLightSource(lightSourceGreen);
    
    MulderRuntime.setAmbientLight(new AmbientLightSource3d(0.3));
  } 

  @Override
  public void runningLoop() {
    controlCamera();  
    handleInput();
  }

  @Override
  public void pausedLoop() {
    controlCamera();
    handleInput();
  }

  int inputDelay = 50;
  public void handleInput() {
    if (inputDelay > 0) {
      inputDelay--;
      return;
    } else {
      inputDelay = 0;
    }
      
    if (keyHasBeenPressed(KeyEvent.VK_F)) {
      inputDelay = 50;
      for (Model3dFace face : teapot.getFaces()) {
        face.setColor(new MulderColor((((Model3dTexturedFace) face).getTextureData().getAverageColor())));
        face.getRenderOptions().toggle(flatShaded);
        face.getRenderOptions().disable(gouraudShaded);
        face.getRenderOptions().disable(meshOnly);
      }
    }
   
    if (keyHasBeenPressed(KeyEvent.VK_G)) {
      inputDelay = 50;
      for (Model3dFace face : teapot.getFaces()) {
        face.setColor(new MulderColor((((Model3dTexturedFace) face).getTextureData().getAverageColor())));
        face.getRenderOptions().toggle(gouraudShaded);
        face.getRenderOptions().disable(flatShaded);
        face.getRenderOptions().disable(meshOnly);
      }
    }
  
    if (keyHasBeenPressed(KeyEvent.VK_T)) {
      inputDelay = 50;
      for (Model3dFace face : teapot.getFaces()) {
        face.setColor(new MulderColor((((Model3dTexturedFace) face).getTextureData().getAverageColor())));
        face.getRenderOptions().toggle(textured);
      }
    }
    
    if (keyHasBeenPressed(KeyEvent.VK_M)) { 
      inputDelay = 50;
      for (Model3dFace face : teapot.getFaces()) {
        face.setColor(MulderColor.black);
        face.getRenderOptions().toggle(meshOnly);
      }
    }
    
    if (keyHasBeenPressed(KeyEvent.VK_C)) {
      inputDelay = 50;
      for (Model3dFace face : teapot.getFaces()) {
        face.setColor(new MulderColor((((Model3dTexturedFace) face).getTextureData().getAverageColor())));
        face.getRenderOptions().toggle(applyLightingColor);
      }
    }
    
    if (keyHasBeenPressed(KeyEvent.VK_S)) {
      inputDelay = 50;
      screenshot("./screenshots/"+System.currentTimeMillis()+".png");
    }
  }

  public static void main(String[] args) {
    var frame = new MulderFrame(800, 600, "Teapot", true, false);
    var demo = new Teapot();
    frame.add("Center", demo);
    frame.setVisible(true); 
    demo.init();
  } 
}