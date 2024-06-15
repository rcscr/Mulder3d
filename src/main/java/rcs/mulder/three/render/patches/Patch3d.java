package rcs.mulder.three.render.patches;

import rcs.mulder.three.gfx.Graphics3d;
import rcs.mulder.three.render.RenderOptions3d;
import rcs.mulder.color.AbstractColorable;
import rcs.mulder.color.MulderColor;
import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;

public abstract class Patch3d extends AbstractColorable { 

  protected RenderOptions3d options;

  public Patch3d(MulderColor color, RenderOptions3d options) {
    super(color); 
    this.options = options;
  }

  public final RenderOptions3d getRenderOptions() {
    return options;
  }
  
  public boolean isTransparent() {
    return color.isTransparent();
  }

  public abstract Vector3d getCenter();

  public abstract void render(Graphics3d graphics, Matrix44 view, Matrix44 projection, Matrix44 viewPort);
}
