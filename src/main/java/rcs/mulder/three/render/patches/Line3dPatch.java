package rcs.mulder.three.render.patches;

import rcs.mulder.three.gfx.Graphics3d;
import rcs.mulder.three.optics.LightingUtils;
import rcs.mulder.three.render.Pipeline3d;
import rcs.mulder.three.render.RenderOptions3d;
import rcs.mulder.three.render.renderers.Line3dRenderer;
import rcs.mulder.color.MulderColor;
import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;

public class Line3dPatch extends Patch3d {
  
  protected Vector3d a, b;
  
  protected Vector3d center;

  public Line3dPatch(Vector3d a, Vector3d b, MulderColor color, RenderOptions3d options) {
    super(color, options);
    this.a = a;
    this.b = b;
  }

  @Override
  public Vector3d getCenter() {
    return null != center 
        ? center
        : (center = a.midPoint(b));
  }

  @Override
  public final void render(Graphics3d graphics, Matrix44 view, Matrix44 projection, Matrix44 viewPort) {
    Vector3d[] viewSpaceCoordinates = Pipeline3d
        .toViewSpaceCoordinates(new Vector3d[] {a, b}, view);
    Vector3d[] clippedSpaceCoordinates = Pipeline3d
        .clipViewSpaceCoordinates(viewSpaceCoordinates);
    
    if (clippedSpaceCoordinates.length < 2) {
      return;
    }
    
    Vector3d[] deviceCoordinates = Pipeline3d.toDeviceCoordinates(clippedSpaceCoordinates, projection, viewPort);

    int colorWithLighting = options.isEnabled(RenderOptions3d.Option.applyLightingColor) 
        ? LightingUtils.applyLightsourceColorTo(color.getRGBA(), 1)
        : color.getRGBA();
        
    Line3dRenderer.render(
        graphics,
        deviceCoordinates[0], 
        deviceCoordinates[1],
        colorWithLighting);
  }
}
