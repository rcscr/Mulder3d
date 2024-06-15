package rcs.mulder.three.gfx;

public interface DepthBuffer {
  
  public int size();
  
  public void clear();
  
  public boolean checkAndSetDepth(int index, double z);
}
