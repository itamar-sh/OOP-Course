package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.GameSpeedChanger;

import java.util.Random;

/**
 * a class deals with a strategy of brick that may change the speed of the game. (via status changer)
 */
public class ChangeGameSpeedStrategy extends RemoveBrickStrategyDecorator{
    private static final int GAME_SPEED_CHANGER_X = 45;
    private static final int GAME_SPEED_CHANGER_Y = 30;
    private static final int NUM_OF_CHANGER_TYPES = 2;
    private static final int SLOWER_INDEX = 0;
    private static final int FASTER_INDEX = 2;
    private static final float MIN_SPEED = 0.9f;
    private static final float MAX_SPEED = 1.1f;
    private static final String QUICK_IMAGE = "assets/quicken.png";
    private static final String SLOW_IMAGE = "assets/slow.png";
    private static final float DEFAULT_TIME_SCALE = 1.0f;
    private final CollisionStrategy toBeDecorated;
    private final WindowController windowController;
    private final ImageReader imageReader;
    private static float curTimeScale = 1.0f;


    /**
     *
     * @param toBeDecorated a classic strategy that we add a new strategy to
     * @param windowController an object that control aspects of the game
     * @param imageReader an object that transform an image to be a Readerable object
     */
    public ChangeGameSpeedStrategy(CollisionStrategy toBeDecorated, WindowController windowController, ImageReader imageReader) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.windowController = windowController;
        this.imageReader = imageReader;
        ChangeGameSpeedStrategy.curTimeScale = DEFAULT_TIME_SCALE;
        this.windowController.setTimeScale(curTimeScale);
    }

    /**
     * A method that change the game speed during some conditions. (if the game is not already too fast or slow)
     * @param typeOfChange 0 to slower and 2 for faster
     */
    public void changeGameSpeed(int typeOfChange, GameSpeedChanger otherObj){
        toBeDecorated.getGameObjectCollection().removeGameObject(otherObj, Layer.STATIC_OBJECTS);
        // slow - green
        if (typeOfChange == SLOWER_INDEX && curTimeScale > MIN_SPEED){
            this.windowController.setTimeScale(MIN_SPEED);
            curTimeScale = MIN_SPEED;
        }
        // quick - red
        if (typeOfChange == FASTER_INDEX && curTimeScale < MAX_SPEED){
            this.windowController.setTimeScale(MAX_SPEED);
            curTimeScale = MAX_SPEED;
        }
    }

    /**
     * when called we will make a new statusChanger that fall to ground. if he hits a paddle the game speed may change.
     * @param thisObj a brick object that has this strategy
     * @param otherObj a ball object that collide with our brick
     * @param counter counter of bricks
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        int typeOfChanger = this.gettypeOfChanger();
        Vector2 location = thisObj.getCenter();
        Vector2 length = new Vector2(GAME_SPEED_CHANGER_X, GAME_SPEED_CHANGER_Y);
        Renderable renderable; // one of rwo cases:
        if (typeOfChanger == FASTER_INDEX){
            renderable = imageReader.readImage(QUICK_IMAGE, true);
        } else { // slow case
            renderable = imageReader.readImage(SLOW_IMAGE, true);
        }
        GameSpeedChanger gameSpeedChanger = new GameSpeedChanger(location, length, renderable,
                typeOfChanger, this);
        this.toBeDecorated.getGameObjectCollection().addGameObject(gameSpeedChanger, Layer.STATIC_OBJECTS);
    }

    /**
     * will return slow or fast changer. based on currently game speed.
     * @return number 2 or zero represents slow or fast index
     */
    private int gettypeOfChanger() {
        if (curTimeScale >= FASTER_INDEX){
            return SLOWER_INDEX;
        }
        if (curTimeScale <= SLOWER_INDEX){
            return FASTER_INDEX;
        }
        Random random = new Random();
        int num = random.nextInt(NUM_OF_CHANGER_TYPES);
        if (num == SLOWER_INDEX){  // 1 is fast and 0 is slow in the toss
            return SLOWER_INDEX;
        }
        return FASTER_INDEX;
    }
}
