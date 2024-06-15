package rcs.mulder.color;

public class AbstractColorable implements Colorable, AlphaEnabled {
  
  protected MulderColor color;

  public AbstractColorable(MulderColor color) {
    setColor(color);
  }

  @Override
  public final MulderColor getColor() {
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
}
