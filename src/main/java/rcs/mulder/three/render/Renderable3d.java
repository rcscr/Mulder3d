package rcs.mulder.three.render;

import rcs.mulder.three.render.patches.Patch3d;

public interface Renderable3d {
  
  public Patch3d[] getRenderablePatches();
}
