package rcs.mulder.three.render;

import rcs.mulder.math.Matrix44;
import rcs.mulder.math.Vector3d;
import rcs.mulder.math.Vector4d;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.view.ViewFrustum3d;

public final class Pipeline3d {  
  
  private static final ViewFrustum3d viewFrustum = MulderRuntime.getView().getViewFrustum();
  
  private Pipeline3d() {
    throw new AssertionError();
  }
  
  public static Vector3d[] toViewSpaceCoordinates(Vector3d[] vertices, Matrix44 view) {
    int size = vertices.length;
    Vector3d[] transformed = new Vector3d[size];
    for (int i = 0; i < size; i++) {
      transformed[i] = vertices[i].affineTransform(view);
    }
    return transformed;
  }
  
  public static Vector3d[][] toViewSpaceCoordinates(Vector3d[] vertices, Vector3d[] normals, Matrix44 view) {
    int size = vertices.length;
    Vector3d[] transformedVertices = new Vector3d[size];
    Vector3d[] transformedNormals  = new Vector3d[size];
    for (int i = 0; i < vertices.length; i++) {
      transformedVertices[i] = vertices[i].affineTransform(view);
      transformedNormals [i] = normals[i].affineTransformAsVector(view);
    }
    return new Vector3d[][]{ transformedVertices, transformedNormals };
  }

  public static Vector3d[] clipViewSpaceCoordinates(Vector3d[] vertices) {
    if (viewFrustum.triviallyNotVisible(vertices)) {
      return new Vector3d[]{};
    }
    return viewFrustum.clipToNearPlane(vertices);
  }

  public static Vector3d[][] clipViewSpaceCoordinates(Vector3d[] vertices, Vector3d[] normals) {
    if (viewFrustum.triviallyNotVisible(vertices)) {
      return new Vector3d[][]{ new Vector3d[]{}, new Vector3d[]{} };
    }
    return viewFrustum.clipToNearPlane(vertices, normals);
  }
  
  public static Vector3d[] toDeviceCoordinates(Vector3d[] vertices, Matrix44 projection, Matrix44 viewPort) {
    Vector3d[] ndcVertices = viewToNormalizedDeviceCoordinates3d(vertices, projection);
    return ndcToDeviceCoordinates(ndcVertices, viewPort);
  }

  public static Vector4d[] toDeviceCoordinates4d(Vector3d[] vertices, Matrix44 projection, Matrix44 viewPort) {
    Vector4d[] ndcVertices = viewToNormalizedDeviceCoordinates4d(vertices, projection);
    return ndcToDeviceCoordinates(ndcVertices, viewPort);
  }

  private static Vector3d[] viewToNormalizedDeviceCoordinates3d(Vector3d[] vertices, Matrix44 projection) {
    int size = vertices.length;
    Vector3d[] ndc = new Vector3d[size];
    for (int i = 0; i < size; i++) {
      Vector4d v4 = projection.mul(vertices[i].toVector4d().w(1));
      if (v4.z() > 0) {
        ndc[i] = v4.homogeneousNormalize().toVector3d();
      } else {
        ndc[i] = v4.pointWiseDivLocal(-v4.w(), -v4.w(), -v4.w(), v4.w()).toVector3d();
      }
    }
    return ndc;
  }

  private static Vector4d[] viewToNormalizedDeviceCoordinates4d(Vector3d[] vertices, Matrix44 projection) {
    int size = vertices.length;
    Vector4d[] ndc = new Vector4d[size];
    for (int i = 0; i < size; i++) {
      Vector4d v4 = projection.mul(vertices[i].toVector4d().w(1));
      double w = v4.w();
      if (v4.z() > 0) {
        ndc[i] = v4.homogeneousNormalizeLocal().w(w);
      } else {
        ndc[i] = v4.divLocal(-w).w(w);
      }
      ndc[i].w(w);
    }
    return ndc;
  }

  private static Vector3d[] ndcToDeviceCoordinates(Vector3d[] vertices, Matrix44 viewPort) {
    int size = vertices.length;
    Vector3d[] vpc = new Vector3d[size];
    for (int i = 0; i < size; i++) {
      vpc[i] = vertices[i].affineTransform(viewPort);
    }
    return vpc;
  }

  private static Vector4d[] ndcToDeviceCoordinates(Vector4d[] vertices, Matrix44 viewPort) {
    int size = vertices.length;
    Vector4d[] vpc = new Vector4d[size];
    for (int i = 0; i < size; i++) {
      vpc[i] = new Vector4d(vertices[i].toVector3d().affineTransform(viewPort), vertices[i].w());
    }
    return vpc;
  }
}