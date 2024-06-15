package rcs.mulder.three.optics;

import rcs.mulder.color.Colorable;
import rcs.mulder.color.MulderColor;
import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.entities.Particle3d;

public class ConstantLightSource3d extends Particle3d implements Colorable, DiffuseLightSource3d {
  
  protected MulderColor color;
  protected double intensity;
  
  public ConstantLightSource3d(double intensity) {
    this(intensity, null);
  }

  public ConstantLightSource3d(double intensity, MulderColor color) {
    this.intensity = intensity;
    this.color = color;
  }

  @Override
  public double getIntensityAt(Vector3d position, Vector3d normal) {
    double alpha = color == null ? 255 : color.getAlpha();
    return intensity * this.position
        .sub(position)
        .normalizeLocal()
        .dotProd(normal.normalize().mulLocal(alpha / 255.0));
  }

  @Override
  public double getIntensityAt(Vector3d position, Vector3d normal, Matrix44 view) {
    double alpha = color == null ? 255 : color.getAlpha();
    return intensity * this.position
        .affineTransform(view)
        .subLocal(position)
        .normalizeLocal()
        .dotProd(normal.normalize().mulLocal(alpha / 255.0));
  }

  @Override
  public MulderColor getColor() {
    return color;
  }

  @Override
  public void setColor(MulderColor color) {
    this.color = color;
  }
}
