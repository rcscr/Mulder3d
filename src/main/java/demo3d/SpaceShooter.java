package demo3d;

import static rcs.mulder.three.render.RenderOptions3d.Option.flatShaded;
import static rcs.mulder.three.render.RenderOptions3d.Option.gouraudShaded;

import java.awt.event.KeyEvent;
import java.io.Serial;
import java.util.Set;

import rcs.mulder.color.MulderColor;
import rcs.mulder.gui.MulderFrame;
import rcs.mulder.math.MathConsts;
import rcs.mulder.math.Matrices;
import rcs.mulder.math.Vector3d;
import rcs.mulder.three.collision.BoundingSphere3d;
import rcs.mulder.three.collision.CollisionHandler3d;
import rcs.mulder.three.collision.CollisionInfo3d;
import rcs.mulder.three.collision.CollisionUtils3d;
import rcs.mulder.three.entities.Rotation3d;
import rcs.mulder.three.entities.models.CollidableModel3d;
import rcs.mulder.three.entities.models.Model3d;
import rcs.mulder.three.entities.models.Model3dFace;
import rcs.mulder.three.entities.models.Model3dFactory;
import rcs.mulder.three.entities.models.Model3dUtils;
import rcs.mulder.three.entities.primitives.Point3d;
import rcs.mulder.three.gfx.TextureRaster;
import rcs.mulder.three.kernel.MulderRuntime;
import rcs.mulder.three.optics.AmbientLightSource3d;
import rcs.mulder.three.optics.ConstantLightSource3d;
import rcs.mulder.utils.AnimationTimer;
import rcs.mulder.utils.XORShift;
import rcs.mulder.utils.struct.MulderArray;
import rcs.mulder.utils.struct.MulderCollection;
import rcs.mulder.utils.struct.MulderLinkedList;

public class SpaceShooter extends Demo3d {

  @Serial
  private static final long serialVersionUID = 1L; 
  
  private final TextureRaster rockTexture =
          Model3dUtils.getImageData(System.getProperty("user.dir") + "/textures/rock.jpg");
  
  private final XORShift xorShift = XORShift.getInstance();

  private final MulderCollection<Point3d> stars = new MulderArray<>(10_000);
  private final MulderCollection<CollidableModel3d> rocks = new MulderLinkedList<>();
  private final MulderCollection<Model3d> shards = new MulderLinkedList<>();
  private final MulderCollection<CollidableModel3d> projectiles = new MulderLinkedList<>();
  
  private final AnimationTimer addRockTimer = new AnimationTimer(this::addNewRocks, 500);
  
  private final RockAndProjectileCollisionHandler rockAndProjectileCollisionHandler =
          new RockAndProjectileCollisionHandler();
  
  @Override
  public void initialize() {
    super.initialize();
    super.setBackgroundColor(MulderColor.black);
    wzc.setAmount(0);
    
    camera.translate(0, 5, 0);
    camera.rotate(Vector3d.NEG_X_AXIS, 20 * MathConsts.DEGREES_TO_RADIANS);
    
    for (int i = 0; i < stars.size(); i++) {
      Point3d star = new Point3d(Vector3d.getRandomUnitVector().mul(400), true);
      
      // these will glimmer on animateStars()
      if (i % 50 == 0) {
        star.setHidden(xorShift.randomDouble() > 0.5);
      }
      
      if (xorShift.randomDouble() > 0.1) {
        star.setColor(MulderColor.darkGray);
      } else if (xorShift.randomDouble() > 0.5) {
        star.setColor(MulderColor.hotPink);
      } else if (xorShift.randomDouble() > 0.5) {
        star.setColor(MulderColor.blue);
      } else {
        star.setColor(MulderColor.white);
      }
      
      stars.add(star);
    }

    MulderRuntime.getRepository().add(stars);
    MulderRuntime.getRepository().add(rocks);
    MulderRuntime.getRepository().add(shards);
    MulderRuntime.getRepository().add(projectiles);
    
    var lightSource = new ConstantLightSource3d(1);
    lightSource.setPosition(0, 10, 0);
    MulderRuntime.addDiffuseLightSource(lightSource);
    MulderRuntime.setAmbientLight(new AmbientLightSource3d(0.1));
  }

  @Override
  public void pausedLoop() { 
    controlCamera();
  }
  
  @Override
  public void runningLoop() { 
    controlCamera();
    handleInput();
    addRockTimer.run();
    animateProjectiles();
    animateRocks();
    animateShards();
    animateStars();
    CollisionUtils3d.forEachCollision(rocks, projectiles, rockAndProjectileCollisionHandler);
  }
  
  int inputDelay = 20;
  public void handleInput() {
    if (inputDelay > 0) {
      inputDelay--;
      return;
    } else {
      inputDelay = 0;
    }
      
    if (keyHasBeenPressed(KeyEvent.VK_SPACE)) {
      inputDelay = 20;
      addNewProjectile();
    }
  }
  
  private void addNewProjectile() {
    double xDotProd = camera.getForwardVector().dotProd(Vector3d.X_AXIS);
    double zDotProd = camera.getForwardVector().dotProd(Vector3d.Z_AXIS);
    Vector3d position = new Vector3d(0, 0, 0);
    Vector3d velocity = new Vector3d(-xDotProd, 0, -zDotProd).normalizeLocal().mul(2);
    CollidableModel3d projecile = (CollidableModel3d) Model3dFactory.pyramid(0.5, 5, 5)
        .setColor(MulderColor.orangeRed)
        .setOuterBoundingObject(new BoundingSphere3d(0.5))
        .setPosition(position)
        .setVelocity(velocity)
        .addTransform(Matrices.create3dRotateMatrix(position, Vector3d.X_AXIS, -MathConsts.HALF_PI))
        .addTransform(Matrices.create3dRotateMatrix(position, Vector3d.Y_AXIS, velocity.angleBetween(Vector3d.NEG_Z_AXIS, Vector3d.NEG_X_AXIS)))
        .build();

    Model3dUtils.setOptions(
        projecile, 
        Set.of(flatShaded), 
        Set.of(gouraudShaded));
    
    projectiles.add(projecile);
  }
  
  private void addNewRocks() {
    double radius = xorShift.randomDouble(0.5, 1.5);
    var rock = (CollidableModel3d) Model3dFactory
        .dodecahedron(radius)
        .setTextureData(rockTexture)
        .setOuterBoundingObject(new BoundingSphere3d(radius))
        .setPosition(new Vector3d(xorShift.randomDouble(-10, 10), 0, -200))
        .setVelocity(new Vector3d(0, 0, 0.5))
        .setColor(MulderColor.rosyBrown)
        .setRotation(Rotation3d.spin(Vector3d.getRandomUnitVector(), 0.05))
        .build();
    
    Model3dUtils.normalizeFacesToTriangles(rock);

    Model3dUtils.deform(rock, 0.2);
    
    Model3dUtils.setOptions(
        rock, 
        Set.of(gouraudShaded), 
        Set.of());
    
    rocks.add(rock);
  }
  
  private void animateProjectiles() {
    long now = System.currentTimeMillis();
    projectiles.forEach(projectile -> {
      projectile.animate();
      if (now - projectile.getTimeOfCreation() > 4000) {
        projectile.destroy();
      }
    });
  }

  private void animateRocks() {
    rocks.forEach(rock -> {
      rock.animate();
      
      boolean outOfBounds = rock.getPosZ() > 0;
      
      if (outOfBounds) {
        rock.destroy();
      }
    });
  }
  
  private void animateShards() {
    shards.forEach(shard -> {
      shard.animate();
      
      if (shard.getPosZ() > 0) {
        shard.destroy();
      }
      
      for (Model3dFace face : shard.getFaces()) {
        if (face.getAlpha() < 1) {
          shard.destroy();
        } else {
          double newAlpha = face.getAlpha() * 0.99;
          face.setAlpha((int) newAlpha);
        }
      }
    });
  }
  
  private void animateStars() {
    stars.forEachWithIndex((star, index) -> {
      if (index % 50 == 0) {
        star.setHidden(!star.isHidden());
      }
    });
  }
  
  private class RockAndProjectileCollisionHandler implements CollisionHandler3d<CollidableModel3d, CollidableModel3d> {

    @Override
    public void handleCollision(CollidableModel3d rock, CollidableModel3d projectile, CollisionInfo3d ci) {
      rock.destroy();
      projectile.destroy();
      addNewShards(rock);
      addNewShards(projectile);
    }

    private void addNewShards(Model3d object) {
      var newShards = Model3dUtils.partition3d(object);
      for (Model3d shard : newShards) {

        Model3dUtils.setOptions(
            shard, 
            Set.of(flatShaded), 
            Set.of(gouraudShaded));
        
        double speed = object.getVelocity().length() * (1 + XORShift.getInstance().randomDouble(-0.5, 0.5));
        Vector3d velocity = Vector3d.getRandomUnitVector().mulLocal(speed).z(object.getVelZ());
        shard.setVelocity(velocity);
        
        Rotation3d rotation = Rotation3d.spin(Vector3d.getRandomUnitVector(), 0.1);
        shard.setRotation(rotation);
        
        shards.add(shard);
      }
    }
  }

  public static void main(String[] args) {
    var frame = new MulderFrame(1000, 800, "Space Shooter Demo", true, false);
    var demo = new SpaceShooter();
    frame.add("Center", demo);
    frame.setVisible(true);
    demo.init();
  }
}
