package rcs.mulder.three.entities.primitives;

import rcs.mulder.color.AlphaEnabled;
import rcs.mulder.color.Colorable;
import rcs.mulder.color.MulderColor;
import rcs.mulder.three.entities.Sprite3d;
import rcs.mulder.three.render.RenderOptions3d;

public abstract class Primitive3d extends Sprite3d implements Colorable, AlphaEnabled {

  protected MulderColor color = MulderColor.black;
  protected RenderOptions3d options = RenderOptions3d.defaults();

  @Override
  public MulderColor getColor() {
    return color;
  }

  @Override
  public void setColor(MulderColor color) {
    this.color = color;
  }

  @Override
  public int getAlpha() {
    return color.getAlpha();
  }

  @Override
  public void setAlpha(int alpha) {
    color = new MulderColor(color.getRed(), color.getGreen(), color.getBlue(), alpha);
  }
  
  public final RenderOptions3d getRenderOptions() {
    return options;
  }
}
