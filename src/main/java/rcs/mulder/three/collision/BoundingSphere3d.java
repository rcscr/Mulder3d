package rcs.mulder.three.collision;

import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;

public class BoundingSphere3d extends BoundingObject3d {
  
  protected double radius;
  
  public BoundingSphere3d(double radius) {
    this(radius, new Vector3d());
  }
  
  public BoundingSphere3d(double radius, Vector3d position) {
    super(position);
    this.radius = radius;
  }

  public double getRadius() {
    return radius;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  } 
  
  @Override
  public void transform(Matrix44 transform) { 
    super.transform(transform); 
    
    radius *= transform.extractScaleX();
  }
 
  @Override
  public boolean contains(Vector3d v3d) {
    return position.distanceSquared(v3d) <= radius*radius;
  }

  @Override
  public CollisionInfo3d computeCollision(BoundingObject3d bo) {
    if (bo instanceof BoundingSphere3d bs) {
      return CollisionDetection3d.computeCollision(this, bs);
    }
    if (bo instanceof BoundingBox3d bb) {
      return CollisionDetection3d.computeCollision(this, bb);
    }
    throw new UnsupportedOperationException();
  }
}
