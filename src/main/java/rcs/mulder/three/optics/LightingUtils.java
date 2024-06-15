package rcs.mulder.three.optics;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

import rcs.mulder.color.ColorUtils;
import rcs.mulder.color.MulderColor;
import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.kernel.MulderRuntime;

public class LightingUtils {
  
  public static final double computeLightingIntensity(Vector3d point, Vector3d normal) {
    return computeLightingIntensity(point, normal, null);
  } 

  public static final double computeLightingIntensity(Vector3d point, Vector3d normal, Matrix44 view) {
    double ambient = MulderRuntime.getAmbientLight().getIntensity();
    double intensity = ambient;
    
    DiffuseLightSource3d[] lightSources = MulderRuntime.getDiffuseLightSources();
    
    for (var lightSource : lightSources) {
      double diffuse;
      
      if (view != null) {
        diffuse = lightSource.getIntensityAt(point, normal, view);
      } else {
        diffuse = lightSource.getIntensityAt(point, normal);
      }
      
      intensity += Math.max(0, diffuse);
    }
    
    return intensity;
  } 
  
  public static final int applyLightsourceColorTo(Vector3d position, Vector3d normal, int objectColor) {
    return applyLightsourceColorTo(position, normal, null, objectColor);
  }
  
  public static final int applyLightsourceColorTo(Vector3d position, Vector3d normal, Matrix44 view, int objectColor) {
    DiffuseLightSource3d[] lightSources = MulderRuntime.getDiffuseLightSources();
    int color = objectColor;
    for (var lightSource : lightSources) {      
      MulderColor lsColor = lightSource.getColor();
      if (lsColor != null) {
        double intensity;
        if (view != null) {
          intensity = lightSource.getIntensityAt(position, normal, view);
        } else {
          intensity = lightSource.getIntensityAt(position, normal);
        }
        color = ColorUtils.blendRGB(color, lsColor.getRGBA(), intensity);
      }
    }
    return color;
  }
  
  public static final int applyLightsourceColorTo(int objectColor, double intensity) {
    DiffuseLightSource3d[] lightSources = MulderRuntime.getDiffuseLightSources();
    int color = objectColor;
    for (var lightSource : lightSources) {      
      MulderColor lsColor = lightSource.getColor();
      if (lsColor != null) {
        color = ColorUtils.blendRGB(color, lsColor.getRGBA(), intensity);
      }
    }
    return color;
  }
  
  public static final boolean hasColoredLightsources() {
    return Arrays.stream(MulderRuntime.getDiffuseLightSources())
        .map(DiffuseLightSource3d::getColor)
        .anyMatch(Predicate.not(Objects::isNull));
  }
}
