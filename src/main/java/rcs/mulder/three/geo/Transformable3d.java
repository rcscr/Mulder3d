package rcs.mulder.three.geo;

import rcs.mulder.math.Matrix44;

public interface Transformable3d {
  
  void transform(Matrix44 transform);
  
}
