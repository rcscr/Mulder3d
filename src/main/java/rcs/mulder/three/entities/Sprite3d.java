package rcs.mulder.three.entities;

import rcs.mulder.math.Vector3d;
import rcs.mulder.three.render.Renderable3d;
import rcs.mulder.utils.struct.MulderGarbageCollectable;

public abstract class Sprite3d extends Particle3d implements Renderable3d, MulderGarbageCollectable {
  
  private long timeOfCreation = System.currentTimeMillis();
  
  private boolean hidden = false;
  private boolean destroyed = false;

  public abstract Vector3d getCenterOfMass();

  public final void spin(Vector3d axis, double deg) {
    super.rotate(getCenterOfMass(), axis, deg);
  }
  
  public final long getTimeOfCreation() {
    return timeOfCreation;
  }
  
  public final void setHidden(boolean hidden) {
    this.hidden = hidden;
  }
  
  public final boolean isHidden() {
    return hidden;
  }

  @Override
  public final void destroy() {
    destroyed = true;
  }

  @Override
  public final boolean isDestroyed() {
    return destroyed;
  }
}
