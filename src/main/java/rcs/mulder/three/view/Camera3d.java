package rcs.mulder.three.view;

import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.entities.Particle3d;

public class Camera3d extends Particle3d {

  public Camera3d() {
    super();
  }

  @Override // overrides to synchronize
  public synchronized void translate(Vector3d v3d) {
    super.translate(v3d); 
  }

  @Override // overrides to synchronize
  public synchronized void transform(Matrix44 m4x4) {
    super.transform(m4x4); 
  }
}