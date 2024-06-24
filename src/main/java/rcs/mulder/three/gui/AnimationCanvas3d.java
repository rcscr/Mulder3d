package rcs.mulder.three.gui;

import rcs.mulder.gui.AbstractAnimationCanvas;
import rcs.mulder.three.gfx.Graphics3d;
import rcs.mulder.three.gfx.Graphics3dSynchronized;
import rcs.mulder.three.gfx.Raster;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.kernel.RenderKernel;

public abstract class AnimationCanvas3d extends AbstractAnimationCanvas {
  
  private static final long serialVersionUID = 1L;
  
  private final RenderKernel renderKernel = MulderRuntime.getRenderKernel();

  public AnimationCanvas3d() {
    super(new Graphics3dSynchronized(new Raster(0, 0)));
  }

  @Override
  protected final void render(Graphics3d graphics) {
    renderKernel.renderAll(graphics);
  }

  @Override
  protected void setWidth(int width) {
      MulderRuntime.getView().getViewFrustum().setWidth(width);
  }

  @Override
  protected void setHeight(int height) {
      MulderRuntime.getView().getViewFrustum().setHeight(height);
  }
}
