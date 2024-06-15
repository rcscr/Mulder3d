package rcs.mulder.three.collision;

import rcs.mulder.three.entities.IParticle3d;

public interface Collidable3d extends IParticle3d {
    
  BoundingObject3d getOuterBoundingObject(); 
}
