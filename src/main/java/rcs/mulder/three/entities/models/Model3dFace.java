package rcs.mulder.three.entities.models;

import rcs.mulder.color.AbstractColorable;
import rcs.mulder.color.MulderColor;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.render.RenderOptions3d;
import rcs.mulder.three.render.RenderOptions3d.Option;
import rcs.mulder.three.render.patches.GouraudPolygon3dPatch;
import rcs.mulder.three.render.patches.Polygon3dPatch;

public class Model3dFace extends AbstractColorable {
  
  protected RenderOptions3d options = RenderOptions3d.defaults();
  
  protected int[] indices;
  
  protected Vector3d lastCameraPosition;
  protected Vector3d[] lastVertices;
  protected Polygon3dPatch lastPatch;

  public Model3dFace(int[] indices, MulderColor color) {
    super(color);
    this.indices = indices;
  }
  
  public Model3dFace cloneWithNewIndices(int[] indices) {
    Model3dFace newFace = new Model3dFace(indices, color);
    newFace.setRenderOptions(options);
    return newFace;
  }

  public int[] getIndices() {
    return indices.clone();
  }

  public RenderOptions3d getRenderOptions() {
    return options;
  }

  void setRenderOptions(RenderOptions3d options) {
    this.options = options;
  }

  public Polygon3dPatch makePatch(Model3dVertices vertices) {
    if (matchesLastPatch(vertices)) {
      return lastPatch;
    }
    
    synchronized(this) {
      lastVertices = getVertices(vertices.getVertices());
      lastCameraPosition = MulderRuntime.getView().getCamera().getPosition();
    }
    
    Polygon3dPatch newPatch;
    if (vertices instanceof Model3dGouraudVertices mgv && options.isEnabled(Option.gouraudShaded)) {
      newPatch = new GouraudPolygon3dPatch(
          lastVertices, 
          getVertices(mgv.getNormals()),
          color,
          options);
    } else {
      newPatch = new Polygon3dPatch(
          lastVertices, 
          color,
          options);
    }
      
    return lastPatch = newPatch;  
  }
  
  protected synchronized boolean matchesLastPatch(Model3dVertices vertices) {
    // assumes that normals only change if vertices changed
    if (lastCameraPosition == null || !lastCameraPosition.equals(MulderRuntime.getView().getCamera().getPosition())) {
      return false;
    }
    if (lastVertices == null || lastPatch == null) {
      return false;
    }
    Vector3d[] newVertices = vertices.getVertices();
    for (int i = 0; i < indices.length; i++) {
      if (!newVertices[indices[i]].equals(lastVertices[i])) {
        return false;
      }
    }
    return true;
  }

  protected Vector3d[] getVertices(Vector3d[] vertices) {
    Vector3d[] faceVertices = new Vector3d[indices.length];
    for (int i = 0; i < indices.length; i++) {
      faceVertices[i] = vertices[indices[i]];
    }
    return faceVertices;
  }
}
