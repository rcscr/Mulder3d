package rcs.mulder.three.render.renderers;

import java.util.Optional;

import rcs.mulder.color.ColorUtils;
import rcs.mulder.math.MathUtils;
import rcs.mulder.math.Vector2d;
import rcs.mulder.math.Vector3d;
import rcs.mulder.math.Vector4d;
import rcs.mulder.three.gfx.Graphics3d;
import rcs.mulder.three.gfx.TextureRaster;

public class GouraudTexturedPolygon3dRenderer {
  
  public static void render(
      Graphics3d graphics, 
      Vector4d[] deviceCoordinates,
      double[] intensities, 
      Optional<int[]> colors,
      TextureRaster textureData, 
      Vector2d[] textureCoordinates,
      int alpha) {

    if (deviceCoordinates.length != 3) {
      throw new IllegalArgumentException("Can only render triangles");
    }

    Vector4d va = deviceCoordinates[0];
    Vector4d vb = deviceCoordinates[1];
    Vector4d vc = deviceCoordinates[2];
      
    double xa = va.x();
    double xb = vb.x();
    double xc = vc.x();
    double xd = (xa+xb+xc)/3;
    
    double ya = va.y();
    double yb = vb.y();
    double yc = vc.y();
    double yd = (ya+yb+yc)/3; 
    
    double za = va.z();
    double zb = vb.z();
    double zc = vc.z();
    double zd = (za+zb+zc)/3;
    
    double u1 = xb - xa;
    double v1 = xc - xa;
    double u2 = yb - ya;
    double v2 = yc - ya;
    double u3 = zb - za;
    double v3 = zc - za;
    
    double aa = u2*v3 - u3*v2;
    double bb = u3*v1 - u1*v3;
    double cc = u1*v2 - u2*v1;
    
    double dInvZdx = -aa/cc;
    double dZdy = -bb/cc;

    boolean interpolateColor = colors.isPresent();
    
    int tdw = textureData.getWidth();    
    int tdh = textureData.getHeight();
    
    int screenW = graphics.getRaster().getWidth();
    int screenH = graphics.getRaster().getHeight(); 

    int ymin = MathUtils.roundToInt(MathUtils.max(MathUtils.min(ya, yb, yc), 0));
    int ymax = MathUtils.roundToInt(MathUtils.min(MathUtils.max(ya, yb, yc), screenH));
    
    double A = Math.abs(cc) / 2;   
    double ak0 = yc*(xb-xc)-xc*(yb-yc);  
    double ak1 = yb-yc;  
    double ak2 = xb-xc;            
    double bk0 = yc*(xa-xc)-xc*(ya-yc);  
    double bk1 = ya-yc;  
    double bk2 = xa-xc;  

    for (int y = ymin; y <= ymax; y++) {
      double ximin = Integer.MIN_VALUE;
      double xjmin = Integer.MIN_VALUE;
      double xkmin = Integer.MIN_VALUE;
      double ximax = Integer.MAX_VALUE;
      double xjmax = Integer.MAX_VALUE;
      double xkmax = Integer.MAX_VALUE; 
      
      if ((y - yb) * (y - yc) <= 0 && yb != yc) {
        ximax = xc + (y - yc)/(yb - yc) * (xb - xc);
        ximin = ximax;
      }
      if ((y - yc) * (y - ya) <= 0 && yc != ya) {
        xjmax = xa + (y - ya)/(yc - ya) * (xc - xa);
        xjmin = xjmax;
      }
      if ((y - ya) * (y - yb) <= 0 && ya != yb) {
        xkmax = xb + (y - yb)/(ya - yb) * (xa - xb);
        xkmin = xkmax;
      }
      
      int xmin = MathUtils.roundToInt(MathUtils.max(MathUtils.min(ximax, xjmax, xkmax), 0));
      int xmax = MathUtils.roundToInt(MathUtils.min(MathUtils.max(ximin, xjmin, xkmin), screenW));

      double fa = intensities[0] / A;  
      double fb = intensities[1] / A;  
      double fc = intensities[2] / A;  
      
      double aMin = 0.5 * Math.abs(ak0 + xmin*ak1 - y*ak2);   
      double bMin = 0.5 * Math.abs(bk0 + xmin*bk1 - y*bk2); 
      double shadeMin = fa*(aMin) + fb*(bMin) + fc*(A - aMin - bMin); 

      double aMax = 0.5 * Math.abs(ak0 + xmax*ak1 - y*ak2);   
      double bMax = 0.5 * Math.abs(bk0 + xmax*bk1 - y*bk2); 
      double shadeMax = fa*(aMax) + fb*(bMax) + fc*(A - aMax - bMax); 

      double dShadeFactorDx = (shadeMax-shadeMin) / (xmax-xmin); 
      double shadeFactor = shadeMin + (1-aMin/A)*dShadeFactorDx + (xmin-xmin)*dShadeFactorDx;

      double invZ = zd + (y-yd)*dZdy + (xmin-xd)*dInvZdx;
      
      for (int x = xmin; x < xmax; x++, invZ += dInvZdx, shadeFactor += dShadeFactorDx) {
        
        Vector3d bary = RenderUtils.cartesianToBarycentricPerspectiveCorrect(x, y, va, vb, vc);
        
        Vector2d interpolatedTextureCoordinate = RenderUtils.barycentricToCartesian(
            bary, textureCoordinates[0], textureCoordinates[1], textureCoordinates[2]);

        int textureX = MathUtils.clamp((int) interpolatedTextureCoordinate.x(), 0, tdw - 1);
        int textureY = MathUtils.clamp((int) interpolatedTextureCoordinate.y(), 0, tdh - 1);

        int pixel = textureData.getPixel(textureX, textureY);

        int colorWithIntensity = ColorUtils.mulRGB(pixel, shadeFactor);
        
        if (interpolateColor) {
          int[] colorz = colors.get();
          
          int interpolatedColor = ColorUtils.addRGBA(
              ColorUtils.mulRGBA(colorz[0], bary.x()),
              ColorUtils.addRGBA(
                  ColorUtils.mulRGBA(colorz[1], bary.y()),
                  ColorUtils.mulRGBA(colorz[2], bary.z())));
          
          colorWithIntensity = ColorUtils.blendRGB(
              colorWithIntensity, 
              interpolatedColor, 
              shadeFactor);
        }

        int finalColor = ColorUtils.setAlphaToRGBA(colorWithIntensity, alpha);
        
        graphics.putPixel(x, y, invZ, finalColor);
      } 
    } 
  }
}