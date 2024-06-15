package rcs.mulder.three.kernel;

import rcs.mulder.three.optics.AmbientLightSource3d;
import rcs.mulder.three.optics.DiffuseLightSource3d;

import java.util.ArrayList;
import java.util.List;

import rcs.mulder.math.TrigLookUp;

public class MulderRuntime {
  
  static { 
    TrigLookUp.init(0.1);
  }
  
  private static final View view = new View();
  
  private static final ObjectRepository repository = new ObjectRepository();
  
  private static final RenderKernel renderKernel = new RenderKernel(repository);
  
  private static List<DiffuseLightSource3d> lightSources = new ArrayList<>();
  
  private static AmbientLightSource3d ambientLight = new AmbientLightSource3d(1);

  public static View getView() {
    return view;
  }
  
  public static RenderKernel getRenderKernel() {
    return renderKernel;
  }
  
  public static ObjectRepository getRepository() {
    return repository;
  }

  public static DiffuseLightSource3d[] getDiffuseLightSources() {
    return lightSources.toArray(DiffuseLightSource3d[]::new);
  }

  public static void addDiffuseLightSource(DiffuseLightSource3d ls) {
    lightSources.add(ls);
  }

  public static AmbientLightSource3d getAmbientLight() {
    return ambientLight;
  }

  public static void setAmbientLight(AmbientLightSource3d light) {
    ambientLight = light;
  }
}
