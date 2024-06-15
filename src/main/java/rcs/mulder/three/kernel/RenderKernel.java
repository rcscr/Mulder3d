package rcs.mulder.three.kernel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import rcs.mulder.math.Vector3d;
import rcs.mulder.three.gfx.Graphics3d;
import rcs.mulder.three.render.patches.Patch3d;
import rcs.mulder.three.view.Camera3d;

public final class RenderKernel {
  
  private static final Camera3d camera = MulderRuntime.getView().getCamera();

  private static final Comparator<Patch3d> DEPTH_COMPARATOR = (a, b) -> {
    Vector3d cameraPos = camera.getPosition();
    
    double aDepth = a.getCenter().distanceSquared(cameraPos);
    double bDepth = b.getCenter().distanceSquared(cameraPos);
    
    return Double.compare(bDepth, aDepth);
  };

  private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

  private final Collection<Patch3d> alphaBuffer = new ArrayList<>();
  
  private final ObjectRepository repository;

  public RenderKernel(ObjectRepository repository) {
    this.repository = repository;
  }

  public void renderAll(Graphics3d graphics) {
    View view = MulderRuntime.getView();
    
    var viewMatrix = view.getViewSpaceMatrix();
    var projMatrix = view.getPerspectiveProjectionMatrix();
    var viewPortMatrix = view.getViewPortMatrix();

    List<Future<?>> futures = repository.patches()
        .map(patch -> executorService.submit(() -> {
          if (patch.isTransparent()) {
            synchronized(alphaBuffer) {
              alphaBuffer.add(patch);
            }
          } else {
            patch.render(graphics, viewMatrix, projMatrix, viewPortMatrix);
          }
        }))
        .collect(Collectors.toList());

    for (Future<?> future : futures) {
      try {
        future.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
    
    try {
      alphaBuffer.stream()
          .sorted(DEPTH_COMPARATOR)
          .forEach(patch -> patch.render(graphics, viewMatrix, projMatrix, viewPortMatrix));
    } catch (IllegalArgumentException e) {
      // this error happens once in a while due to unpredictable 
      // comparison of doubles (floating-point arithmetic); safe to ignore
      boolean isFloatingPointError = e.getMessage().equals("Comparison method violates its general contract!");
      
      if (!isFloatingPointError) {
        throw e;
      }
    }
     
    alphaBuffer.clear();
  }
}