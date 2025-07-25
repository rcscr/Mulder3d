package rcs.mulder.math;

import rcs.mulder.physics.Conversions;

public class MulderConversionLookUp {

  private static int size;

  private static double[] pixelsToMeters;
  private static double[] pixelsSquaredToSpeed;

  public MulderConversionLookUp(int s) {
    size = s;
    pixelsToMeters = new double[size];
    pixelsSquaredToSpeed = new double[size];
    for (int i = 0; i < size; i++) {
      pixelsToMeters[i] = Conversions.pixelsToMeters(i);
      pixelsSquaredToSpeed[i] = Conversions.pixelsToSpeed(Math.sqrt(i));
    }
  }

  public static double pixelsToMeters(double pixels) {
    if (pixels > size) {
      return Conversions.pixelsToMeters(pixels);
    }
    return pixelsToMeters[(int) pixels];
  }

  public static double pixelsSquaredToSpeed(double pixelsSquared) {
    if (pixelsSquared > size) {
      return Conversions.pixelsToSpeed(Math.sqrt(pixelsSquared));
    }
    return pixelsSquaredToSpeed[(int) pixelsSquared];
  }
}