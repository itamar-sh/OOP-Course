package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
* a class deals with a strategy of brick that add a new paddle to the middle of the board
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator{
    private static final int NUM_BALL_COLLISIONS_TO_TURN_OFF = 4;
    private static final float ZOOM_IN_BY = 1.2f;
    private static BallCollisionCountdownAgent ballCollisionCountdownAgent;
    private final CollisionStrategy toBeDecorated;
    private final WindowController windowController;
    private final BrickerGameManager gameManager;

    /**
     *
     * @param toBeDecorated a classic strategy that we add a new strategy to
     * @param windowController an object that control aspects of the game
     * @param gameManager the main class that control a game
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated, danogl.gui.WindowController windowController,
                                BrickerGameManager gameManager){
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * A method that turns off the camera and remove the agent that controlled the current following of object
     */
    public void turnOffCameraChange(){
        this.toBeDecorated.getGameObjectCollection().removeGameObject(ChangeCameraStrategy.ballCollisionCountdownAgent,
                Layer.BACKGROUND);
        this.gameManager.setCamera(null);
    }

    /**
     * when called we will make a new agent that follow an object until it hits another object a couple of times.
     * in the meantime the camera will also follow this object.
     * @param thisObj a brick object that has this strategy
     * @param otherObj a ball object that collide with our brick
     * @param counter counter of bricks
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        // make sure we get the main ball to look at
        Ball gameBall = (Ball)otherObj;
        for (GameObject obj : toBeDecorated.getGameObjectCollection()){
            if (obj instanceof Ball && !(obj instanceof Puck)){
                gameBall = (Ball) obj;
            }
        }
        // sets the camera change
        this.gameManager.setCamera(
                new Camera(
                        gameBall, 			//object to follow
                        Vector2.ZERO, 	//follow the center of the object
                        windowController.getWindowDimensions().mult(ZOOM_IN_BY),  //widen the frame a bit
                        windowController.getWindowDimensions()   //share the window dimensions
                )
        );
        // sets agent that responsible to turning off the camera change
        ChangeCameraStrategy.ballCollisionCountdownAgent = new BallCollisionCountdownAgent(gameBall, this,
                NUM_BALL_COLLISIONS_TO_TURN_OFF);
        this.toBeDecorated.getGameObjectCollection().addGameObject(ballCollisionCountdownAgent, Layer.BACKGROUND);
    }
}
