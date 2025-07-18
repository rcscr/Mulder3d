package rcs.mulder.three.render.renderers;

import rcs.mulder.math.Vector2d;
import rcs.mulder.math.Vector3d;
import rcs.mulder.math.Vector4d;
import rcs.mulder.utils.HexaConsumer;
import rcs.mulder.utils.TriConsumer;

public class RenderUtils {
  
  public static void triangulate(
      Vector3d[] vertices,
      TriConsumer<Vector3d, Vector3d, Vector3d> consumer) {

    triangulateWithIndex(vertices, (va, vb, vc, a, b, c) -> {
      consumer.accept(va, vb, vc);
    });
  }

  public static <T>  void triangulateWithIndex(
      T[] objects, 
      HexaConsumer<T, T, T, Integer, Integer, Integer> consumer) {
    
    for (int i = 1; i < objects.length-1; i++) { 
      int ia = 0;
      int ib = i;
      int ic = i+1;
      T va = objects[ia];
      T vb = objects[ib];
      T vc = objects[ic];
      consumer.accept(va, vb, vc, ia, ib, ic);
    }    
  }
  
  public static Vector3d cartesianToBarycentricPerspectiveCorrect(double x, double y, Vector4d a, Vector4d b, Vector4d c) {
    double x1 = a.x();
    double x2 = b.x();
    double x3 = c.x();
    double y1 = a.y();
    double y2 = b.y();
    double y3 = c.y();
    
    double y2y3 = y2 - y3;
    double x3x2 = x3 - x2;
    double x1x3 = x1 - x3;
    double y1y3 = y1 - y3;
    double y3y1 = y3 - y1;
    double xx3  = x  - x3;
    double yy3  = y  - y3;
    
    double d = y2y3 * x1x3 + x3x2 * y1y3;
    double lambda1 = (y2y3 * xx3 + x3x2 * yy3) / d;
    double lambda2 = (y3y1 * xx3 + x1x3 * yy3) / d;
    
    Vector3d bary = new Vector3d(
      lambda1,
      lambda2,
      1 - lambda1 - lambda2);

    // perspective correction
    return bary.pointWiseDiv(a.w(), b.w(), c.w())
        .divLocal(
            bary.x() / a.w() +
                bary.y() / b.w() +
                bary.z() / c.w());
  }

  public static Vector2d barycentricToCartesian(Vector3d bary, Vector2d a, Vector2d b, Vector2d c) {
    return new Vector2d(
        bary.x() * a.x() + bary.y() * b.x() + bary.z() * c.x(),
        bary.x() * a.y() + bary.y() * b.y() + bary.z() * c.y());
  }
}
