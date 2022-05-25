package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.clouds.Cloud;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;

import java.awt.*;

/**
 * Class represents the Game.
 * Control all the object and the game flow.
 * All the methods that create objects start with "create"
 * In the Update the game is changing everything by their role.
 */
public class PepseGameManager extends GameManager {
    // init constants
    // game
    private static  Vector2 windowDimensions;
    private static final Vector2 DELTA_RELATION_TO_OBJECT_FROM_CAMERA = Vector2.UP.mult(30);
    // background
    private static final Color HaloColor = new Color(255, 255, 0, 20);
    private static final int GROUND_LAYER = Layer.STATIC_OBJECTS;
    private static final float CYCLE_LENGTH = 30f;
    private static final int SUN_HALO_LAYER = Layer.BACKGROUND + 10;
    // tress and leaves
    private static final int LEAF_LAYER = Layer.FOREGROUND;
    private static final int SEED = 100;

    // fields
    private Avatar avatar;
    private int curLimitRight;
    private int curLimitLeft = -300;
    private int spaceForChecking;
    private int spaceForExpanding;
    private enum LIMITS {RIGHT,LEFT}
    private Tree tree;
    private Terrain terrain;
    private LIMITS curLimit;
    private static final float DIVIDER = 2;
    private static final float PAD_FOR_AVATAR_BEGIN = -100f;
    private static final int CHECKING_DIVIDER = 50;
    private static final int EXPANDING_DIVIDER = 60;
    private static final int LIMIT_PAD = 300;
    private Cloud cloud;

    //main function
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    /**
     *  init function. Create all the objects required for the game to start.
     * @param imageReader object that can make an image to renderable object
     * @param soundReader object that can make an sound to renderable object
     * @param inputListener Contains a single method: isKeyPressed, which returns whether if a key is pressed
     * @param windowController object that control the gameObject
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        spaceForChecking = ((int)windowDimensions.x()/CHECKING_DIVIDER + 1) * Block.SIZE;
        spaceForExpanding = spaceForChecking/EXPANDING_DIVIDER * Block.SIZE;
        curLimitRight =  (int) (windowDimensions.x() + LIMIT_PAD);
        Sky.create(gameObjects(), windowDimensions, Layer.BACKGROUND);
        // terrain
        terrain = new Terrain(gameObjects(), GROUND_LAYER, windowDimensions, SEED);
        terrain.createInRange(curLimitLeft, curLimitRight);
        // trees
        tree = new Tree(gameObjects(),Layer.DEFAULT, LEAF_LAYER,
                windowDimensions, terrain::groundHeightAt, SEED);
        tree.createInRange(curLimitLeft, curLimitRight);
        gameObjects().layers().shouldLayersCollide(LEAF_LAYER, GROUND_LAYER, true);
        // background
        Night.create(gameObjects(), Layer.FOREGROUND-1, windowDimensions, CYCLE_LENGTH);
        GameObject sun = Sun.create(gameObjects(), Layer.BACKGROUND, windowDimensions, CYCLE_LENGTH);
        SunHalo.create(gameObjects(), SUN_HALO_LAYER, sun, HaloColor);
        // avatar
        avatar = Avatar.create(gameObjects(), Layer.DEFAULT, new Vector2(
                windowDimensions.x() / DIVIDER, PAD_FOR_AVATAR_BEGIN), inputListener, imageReader);
        // clouds
        cloud = new Cloud(gameObjects(), Layer.DEFAULT, windowDimensions,
                SEED, imageReader);
        cloud.createInRange(curLimitLeft, curLimitRight);
        // set camera
        setCamera(new Camera(avatar, DELTA_RELATION_TO_OBJECT_FROM_CAMERA,
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()));
    }

    /**
     *  This is the main method that been performed all the time in cycling.
     *  Control the changes in the game. especially for the game background (infinite world).
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // case of getting too right from the current world existed
        if (avatar.getCenter().x() > curLimitRight - spaceForChecking) {
            // create continuous game
            terrain.createInRange(curLimitRight, curLimitRight + spaceForExpanding);
            tree.createInRange(curLimitRight, curLimitRight + spaceForExpanding);
            cloud.createInRange(curLimitRight, curLimitRight + spaceForExpanding);
            curLimitRight += spaceForExpanding;
        }
        // case of getting too left from the current world existed
        if (avatar.getCenter().x() < curLimitLeft + spaceForChecking) {
            // create continuous game
            terrain.createInRange(curLimitLeft - spaceForExpanding, curLimitLeft);
            tree.createInRange(curLimitLeft - spaceForExpanding, curLimitLeft);
            cloud.createInRange(curLimitLeft - spaceForExpanding, curLimitLeft);
            curLimitLeft -= spaceForExpanding;
        }
        // deleting exist but far world that we don't need right now - from the right of the avatar
        if (avatar.getCenter().x() < curLimitRight - windowDimensions.x() - spaceForExpanding) {
            curLimit = LIMITS.RIGHT;
            curLimitRight -= spaceForExpanding;
//            gameObjects().forEach(this::delete_action);
            delete();
        }
        // deleting exist but far world that we don't need right now - from the right of the avatar
        if (avatar.getCenter().x() > curLimitLeft + windowDimensions.x() + spaceForExpanding) {
            curLimit = LIMITS.LEFT;
            curLimitLeft += spaceForExpanding;
            //gameObjects().forEach(this::delete_action);
            delete();

        }
    }

    /**
     * delete object if is meant to be deleted (in the specific area)
     */
    private void delete() {
        for (GameObject obj : gameObjects().objectsInLayer(GROUND_LAYER)) {
            if (toDelete(obj)) gameObjects().removeGameObject(obj, GROUND_LAYER);
        }
        for (GameObject obj : gameObjects().objectsInLayer(GROUND_LAYER + 1)) {
            if (toDelete(obj)) gameObjects().removeGameObject(obj, GROUND_LAYER);
        }
        for (GameObject obj : gameObjects().objectsInLayer(Layer.DEFAULT)) {
            if (toDelete(obj)) gameObjects().removeGameObject(obj, Layer.DEFAULT);
        }
        for (GameObject obj : gameObjects().objectsInLayer(LEAF_LAYER)) {
            if (toDelete(obj)) gameObjects().removeGameObject(obj, LEAF_LAYER);
        }

    }

    /**
     *  check if an object is in the area that meant to delete right now. (far from the avatar)
     * @param obj obj to check
     * @return whether he has to be deleted
     */
    private boolean toDelete(GameObject obj) {
        if (curLimit == LIMITS.RIGHT)
            return obj.getCenter().x() > curLimitRight;
        return obj.getCenter().x() < curLimitLeft;
    }
}

