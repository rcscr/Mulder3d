package rcs.mulder.three.kernel;

import rcs.mulder.gui.AbstractAnimationCanvas;
import rcs.mulder.physics.Conversions;
import rcs.mulder.physics.Gravity1d;
import rcs.mulder.physics.PhysicsConsts;
import rcs.mulder.math.MathUtils;
import rcs.mulder.math.Vector3d;

public class MulderEnvironment {

  public static final double PIXELS_PER_METER; 
  public static final Vector3d WORLD_UP;
  public static final Gravity1d GRAVITY;
  public static final Vector3d GRAVITY_ACCEL;
  
  static {
    PIXELS_PER_METER = 1;
    WORLD_UP = Vector3d.Y_AXIS;
    GRAVITY = new Gravity1d(
            Conversions.metersToPixels(
                    - Math.abs(PhysicsConsts.EARTH_GRAVITY_METERS_PER_SEC) 
                    * MathUtils.squared(AbstractAnimationCanvas.FPS_DELAY_MS / 1000.0)), 
            WORLD_UP);
    GRAVITY_ACCEL = GRAVITY.getAcceleration();
  }
}
