package rcs.mulder.event;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import rcs.mulder.math.MathUtils;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.view.Camera3d;

public class WheelZoomCamera implements MouseWheelListener {
  
  private Camera3d camera;

  private boolean wheelScrolled = false;
  private double preciseWheelRotation = 0;
  private double amount  = 1;    
  private int counter = 0;       

  public WheelZoomCamera(Camera3d camera, double amount) {
    this.camera = camera;
    this.amount = amount;
  }

  public Camera3d getCamera() {
    return camera;
  }

  public void setCamera(Camera3d camera3d) {
    this.camera = camera3d;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public void update() {
    if (wheelScrolled) {
      int sign = MathUtils.signum(preciseWheelRotation);
      
      if (counter++ > sign * preciseWheelRotation / amount) {
        wheelScrolled = false;
        counter = 0;
      } else {
        Vector3d delta = camera.getForwardVector().mulLocal(sign * amount / (double) counter);
        camera.translate(delta);
      }
    } 
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    preciseWheelRotation = e.getPreciseWheelRotation();
    wheelScrolled = true;
    counter = 0;
  }   
}
