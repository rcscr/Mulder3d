package rcs.mulder.physics;

import rcs.mulder.gui.AbstractAnimationCanvas;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.kernel.MulderEnvironment;

public final class Conversions {

  private Conversions() {
    throw new AssertionError();
  }

  public static Vector3d positionToPixels(Vector3d positionInMeters) {
    return new Vector3d(
            metersToPixels(positionInMeters.x()),
            metersToPixels(positionInMeters.y()), 
            metersToPixels(positionInMeters.y()));
  }

  public static Vector3d pixelsToPosition(Vector3d positionInPixels) {
    return new Vector3d(
            pixelsToMeters(positionInPixels.x()), 
            pixelsToMeters(positionInPixels.y()), 
            pixelsToMeters(positionInPixels.y()));
  }

  public static Vector3d velocityToPixels(Vector3d velocityInMetersPerSec) {
    return new Vector3d(
            speedToPixels(velocityInMetersPerSec.x()),
            speedToPixels(velocityInMetersPerSec.y()), 
            speedToPixels(velocityInMetersPerSec.z()));
  }

  public static Vector3d pixelsToVelocity(Vector3d velocityInPixelsPerDelay) {
    return new Vector3d(
            pixelsToSpeed(velocityInPixelsPerDelay.x()), 
            pixelsToSpeed(velocityInPixelsPerDelay.y()),
            pixelsToSpeed(velocityInPixelsPerDelay.z()));
  }

  public static double speedToPixels(double metersPerSec) {
    return metersToPixels(metersPerSec * AbstractAnimationCanvas.FPS_DELAY_MS / 1000.0);
  }

  public static double pixelsToSpeed(double pixelsPerDelay) {
    return pixelsToMeters(pixelsPerDelay / AbstractAnimationCanvas.FPS_DELAY_MS / 1000.0);
  }

  public static double metersToPixels(double meters) {
    return meters * MulderEnvironment.PIXELS_PER_METER;
  }

  public static double pixelsToMeters(double pixels) {
    return pixels / MulderEnvironment.PIXELS_PER_METER;
  }
}