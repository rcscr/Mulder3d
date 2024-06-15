package rcs.mulder.three.render.renderers;

import rcs.mulder.math.MathUtils;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.gfx.Graphics3d;

public class Point3dRenderer {

  public static void render(Graphics3d graphics, Vector3d deviceCoordinate, int color) { 
    graphics.putPixel(
        MathUtils.roundToInt(deviceCoordinate.x()), 
        MathUtils.roundToInt(deviceCoordinate.y()), 
        deviceCoordinate.z(), 
        color);
  }
}