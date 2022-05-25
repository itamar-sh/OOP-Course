package pepse.world.clouds;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.GameObjectPhysics;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.Objects;
import java.util.Random;

/**
 * class used to create the clouds of the game
 */

public class Cloud {
    private static final String CLOUD_IMAGE = "pepse/assets/cloud-computing (1).png";
    private static final Vector2 DIMENSIONS = new Vector2(150,90);
    private final GameObjectCollection gameObjects;
    private final int cloudLayer;
    private final int seed;
    private final Renderable cloudImage;
    private static final int RANDOM_UPPER_BOUND = 9;
    private static final int RANDOM_BIGGER_COND = 6;
    private static final int WINDOW_DIVIDER = 10;
    private static final int DIVIDER = 2;
    private final int limit;

    /**
     * Constructor
     * @param gameObjects object that control all the objects in the game
     * @param cloudLayer number of the layer of the clouds.
     * @param windowDimensions dimensions of the screen
     * @param seed the seed used to generate randomness for the game
     * @param imageReader object that can make an image to renderable object
     */
    public Cloud(GameObjectCollection gameObjects,
                 int cloudLayer, Vector2 windowDimensions,
                 int seed, ImageReader imageReader) {
        this.gameObjects = gameObjects;
        this.cloudLayer = cloudLayer;
        this.seed = seed;
        this.cloudImage = imageReader.readImage(CLOUD_IMAGE, true);
        limit = (int) (windowDimensions.y() / WINDOW_DIVIDER);

    }

    /**
     *
     * @param minX the minimal x that the clouds generated should cover
     * @param maxX the maximal x that the clouds generated should cover
     */
    public void createInRange(int minX, int maxX) {
        if (minX == maxX) return;
        Vector2 pos;
        int numberOfCloudsInWidth = (int) Math.ceil(((float) (maxX - minX) /DIMENSIONS.x() ));
        int startPosX = (int) (maxX - numberOfCloudsInWidth * DIMENSIONS.x());
        for (int i = 0; i < numberOfCloudsInWidth; i++) {
            Random random = new Random(Objects.hash(startPosX*7, seed));
            if (random.nextInt(RANDOM_UPPER_BOUND) > RANDOM_BIGGER_COND) {
                pos = new Vector2(startPosX, -random.nextInt(limit));
                createSingleCloud(pos);
            }
            startPosX+=DIMENSIONS.x()/DIVIDER;
        }
    }

    // creates a single cloud
    private void createSingleCloud(Vector2 cloudPos) {
        GameObject cloud = new GameObject(cloudPos, DIMENSIONS, cloudImage);
        gameObjects.addGameObject(cloud, cloudLayer);
        cloud.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        cloud.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
