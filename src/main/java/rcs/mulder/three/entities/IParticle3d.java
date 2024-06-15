package rcs.mulder.three.entities;

import rcs.mulder.math.Vector3d;
import rcs.mulder.three.geo.Movable3d;
import rcs.mulder.three.geo.Transformable3d;

public interface IParticle3d extends Movable3d, Transformable3d {
  
  double getMass();
  void setMass(double m);
 
  Vector3d getPosition();
  void setPosition(Vector3d pos);
  void setPosition(double x, double y, double z);
  
  Vector3d getVelocity();
  void setVelocity(Vector3d vel);
  void setVelocity(double x, double y, double z);
  
  ReferenceFrame3d getReferenceFrame();
  void setReferenceFrame(ReferenceFrame3d rf);
}
