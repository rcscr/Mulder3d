package rcs.mulder.three.render.patches;

import rcs.mulder.math.Vector4d;
import rcs.mulder.three.geo.GeoUtils3d;
import rcs.mulder.three.gfx.Graphics3d;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.optics.LightingUtils;
import rcs.mulder.three.render.Pipeline3d;
import rcs.mulder.three.render.RenderOptions3d;
import rcs.mulder.three.render.RenderOptions3d.Option;
import rcs.mulder.three.render.renderers.GouraudPolygon3dRenderer;
import rcs.mulder.three.view.ViewUtils;

import rcs.mulder.color.ColorUtils;
import rcs.mulder.color.MulderColor;
import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;

public class GouraudPolygon3dPatch extends Polygon3dPatch {
  
  protected Vector3d[] normals;

  public GouraudPolygon3dPatch(
      Vector3d[] vertices, 
      Vector3d[] normals, 
      MulderColor color,
      RenderOptions3d options) {
    super(vertices, color, options);
    this.normals = normals;
  }

  @Override
  public final void render(Graphics3d graphics, Matrix44 view, Matrix44 projection, Matrix44 viewPort) {
    if (!options.isEnabled(Option.gouraudShaded)
        || options.isEnabled(Option.meshOnly)) {
      super.render(graphics, view, projection, viewPort);
      return;
    }  
    
    Vector3d center = getCenter();
    Vector3d surfaceNormal = GeoUtils3d.getNormal(vertices);
    boolean isBackfaceToCamera = ViewUtils.isBackFace(MulderRuntime.getView().getCamera().getPosition(), center, surfaceNormal);
    
    if (isBackfaceToCamera && shouldCullIfBackface()) {
      return;
    }
    
    Vector3d[][] viewSpaceCoordinates = Pipeline3d.toViewSpaceCoordinates(vertices, normals, view);
    Vector3d[] viewVertices = viewSpaceCoordinates[0];
    Vector3d[] viewNormals = viewSpaceCoordinates[1];

    Vector3d[][] clippedViewSpaceCoordinates = Pipeline3d.clipViewSpaceCoordinates(viewVertices, viewNormals);
    Vector3d[] clippedViewVertices = clippedViewSpaceCoordinates[0];
    Vector3d[] clippedViewNormals = clippedViewSpaceCoordinates[1];
    
    int numClippedViewVertices = clippedViewVertices.length;
    if (numClippedViewVertices < 3) {
      return;
    }

    Vector3d[][] triangulatedClippedViewVertices = GeoUtils3d.triangulate(clippedViewVertices);

    boolean shouldReverseNormalForLighting = !options.isEnabled(Option.bothSidesShaded) && isBackfaceToCamera;
    
    double[] intensities = new double[numClippedViewVertices];      
    for (int j = 0; j < intensities.length; j++) {
      intensities[j] = LightingUtils.computeLightingIntensity(
          clippedViewVertices[j], 
          shouldReverseNormalForLighting ? clippedViewNormals[j].mul(-1) : clippedViewNormals[j],
          view);
    }

    boolean shouldApplyLightingColor = options.isEnabled(Option.applyLightingColor) && LightingUtils.hasColoredLightsources();
    
    int[] colors = new int[numClippedViewVertices];
    for (int k = 0; k < numClippedViewVertices; k++) {
      colors[k] = color.getRGBA();
      if (shouldApplyLightingColor) {
        colors[k] = LightingUtils.applyLightsourceColorTo(
            clippedViewVertices[k], 
            shouldReverseNormalForLighting? clippedViewNormals[k].mul(-1) : clippedViewNormals[k], 
            view, 
            colors[k]);
      }
    }

    double[][] triangulatedIntensities = GeoUtils3d.triangulate(intensities);
    int[][] triangulatedColors = GeoUtils3d.triangulate(colors);
    
    for (int i = 0; i < triangulatedClippedViewVertices.length; i++) {
      Vector3d[] triangle = triangulatedClippedViewVertices[i];
      double[] triangleIntensities = triangulatedIntensities[i];
      int[] triangleColors = triangulatedColors[i];

      Vector4d[] deviceCoordinates = Pipeline3d.toDeviceCoordinates4d(triangle, projection, viewPort);

      triangleColors[0] = ColorUtils.mulRGB(triangleColors[0], triangleIntensities[0]);
      triangleColors[1] = ColorUtils.mulRGB(triangleColors[1], triangleIntensities[1]);
      triangleColors[2] = ColorUtils.mulRGB(triangleColors[2], triangleIntensities[2]);

      GouraudPolygon3dRenderer.render(
          graphics,
          deviceCoordinates,
          triangleColors);
    }
  }
}
  
