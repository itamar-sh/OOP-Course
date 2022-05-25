package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

/**
 * a class deals with a strategy of brick that add a new paddle to the middle of the board
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator{
    private final CollisionStrategy toBeDecorated;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private MockPaddle mockPaddle;
    private static final int MIN_DISTANCE_FROM_EDGE = 10;
    private static final int NUM_COLLISION_TO_DISAPPEAR = 3;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final String PADDLE_IMAGE = "assets/paddle.png";

    /**
     * constructor
     * @param toBeDecorated a classic strategy that we add a new strategy to
     * @param imageReader object that can make an image to renderable object
     * @param inputListener Contains a single method: isKeyPressed, which returns whether if a key is pressed
     * @param windowDimensions dimensions of the windows
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions){
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        MockPaddle.isInstantiated = false;
    }

    /**
     * when called we will make a new paddle in the middle of the board. unless we already have one.
     * @param thisObj a brick object that has this strategy
     * @param otherObj a ball object that collide with our brick
     * @param counter counter of bricks
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);  // remove the brick
        // check if the paddle is already exists
        if (this.mockPaddle != null && MockPaddle.isInstantiated){
            return;
        }
        if (MockPaddle.isInstantiated){
            return;
        }
        // make a new paddle
        Renderable paddleImage = imageReader.readImage(
                PADDLE_IMAGE, false);
        Vector2 location = new Vector2(this.windowDimensions.x()/2, this.windowDimensions.y()/2);
        Vector2 length = new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT);
        this.mockPaddle = new MockPaddle(location, length,
                paddleImage, this.inputListener,this.windowDimensions, this.toBeDecorated.getGameObjectCollection(),
                MIN_DISTANCE_FROM_EDGE, NUM_COLLISION_TO_DISAPPEAR);
        // add him to the objects of the game
        this.toBeDecorated.getGameObjectCollection().addGameObject(this.mockPaddle);
    }
}
