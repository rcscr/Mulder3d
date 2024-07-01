package rcs.mulder.three.entities;

import java.util.function.Function;

import rcs.mulder.math.Matrices;
import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;

public class Rotation3d {
  
  private final Function<Particle3d, Vector3d> center;
  private final Function<Particle3d, Vector3d> axis;
  private final Function<Particle3d, Double> radians;

  public static Rotation3d spin(Vector3d axis, double radians) {
    return new Rotation3d(
            Particle3d::getPosition,
            particle -> axis,
            particle -> radians);
  }

  public static Rotation3d yaw(double radians) {
    return new Rotation3d(
            Particle3d::getPosition,
            particle -> particle.up,
            particle -> radians);
  }

  public static Rotation3d roll(double radians) {
    return new Rotation3d(
            Particle3d::getPosition,
            particle -> particle.fw,
            particle -> radians);
  }

  public static Rotation3d pitch(double radians) {
    return new Rotation3d(
            Particle3d::getPosition,
            particle -> particle.sd,
            particle -> radians);
  }
  
  public Rotation3d(
      Function<Particle3d, Vector3d> center,
      Function<Particle3d, Vector3d> axis,
      Function<Particle3d, Double> radians) {
    this.center = center;
    this.axis = axis;
    this.radians = radians;
  }

  public Matrix44 getMatrix(Particle3d particle) {
    return Matrices.create3dRotateMatrix(
        center.apply(particle), 
        axis.apply(particle),
        radians.apply(particle));
  }
}
