package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

/**
 * strategy that removes brick from the collection of th objects of a game.
 */
public class BrickStrategyFactory {
    private static final int NUM_OF_UNIQUE_STRATEGIES = 5;
    private final GameObjectCollection gameObjectCollection;
    private final BrickerGameManager gameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;
    private static final int REMOVE_BRICK_STRATEGY_INDEX = 0;
    private static final int ADD_PADDLE_STRATEGY_INDEX = 1;
    private static final int PUCK_STRATEGY_INDEX = 2;
    private static final int CHANGE_CAMERA_STRATEGY_INDEX = 3;
    private static final int CHANGE_GAME_SPEED_STRATEGY_INDEX = 4;
    private static final int DOUBLE_BEHAVIOUR_STRATEGY_INDEX = 5;


    /**
     *
     * @param gameObjectCollection  collection of all the gameObject in the game
     * @param gameManager class that manage the classes that control the game
     * @param imageReader object that can make an image to Renderable object
     * @param soundReader object that can make an audio file to Sound object
     * @param inputListener Contains a single method: isKeyPressed, which returns whether if a key is pressed
     * @param windowController class that controls the board
     * @param windowDimensions dimensions of the windows
     */
    public BrickStrategyFactory(GameObjectCollection gameObjectCollection, BrickerGameManager gameManager,
                                danogl.gui.ImageReader imageReader, SoundReader soundReader,
                                UserInputListener inputListener, WindowController windowController,
                                Vector2 windowDimensions){

        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
    }

    /**
     * in general, the method give a random strategy. but the math is not so clear since we have the options to get
     * double behaviour and we have one strategy that is basic "RemoveBrick" so if we dont include her as rea strategy.
     * @return an strategy that implement the interface CollisionStrategy
     */
    public CollisionStrategy getStrategy() {
        Random random = new Random();
        int strategy = random.nextInt(NUM_OF_UNIQUE_STRATEGIES+1);

        if (strategy == DOUBLE_BEHAVIOUR_STRATEGY_INDEX) {
            // choice if we have three strategies
            boolean isThirdBehaviour = DOUBLE_BEHAVIOUR_STRATEGY_INDEX ==
                                                            random.nextInt(NUM_OF_UNIQUE_STRATEGIES + 1);
            // simple make 2 strategies that are not the RemoveBrickStrategy
            // we have -1 in the random because we exclude the double behavior strategy
            CollisionStrategy firstStrategy =
                    this.pickOneStrategy(random.nextInt(NUM_OF_UNIQUE_STRATEGIES - 1) + 1);
            CollisionStrategy secondStrategy =
                    this.pickOneStrategy(random.nextInt(NUM_OF_UNIQUE_STRATEGIES - 1) + 1);
            // if we get ThirdBehaviour than we use thirdStrategy.
            CollisionStrategy thirdStrategy;
            if (isThirdBehaviour) {
                thirdStrategy =
                        this.pickOneStrategy(random.nextInt(NUM_OF_UNIQUE_STRATEGIES - 1) + 1);
                return new DoubleBehaviourStrategy(firstStrategy, secondStrategy, thirdStrategy);
            }
            return new DoubleBehaviourStrategy(firstStrategy, secondStrategy);
        }
        return pickOneStrategy(strategy);
    }

    /**
     * we make a new startegy base on a number and return the new instance.
     * @param strategyNumber we return a strategy based on a number. (right now from 0 to 4)
     *                       double behaviour not included.
     * @return an unique strategy
     */
    private CollisionStrategy pickOneStrategy(int strategyNumber){
        RemoveBrickStrategy removeBrickStrategy  = new RemoveBrickStrategy(this.gameObjectCollection);
        switch (strategyNumber) {
            case REMOVE_BRICK_STRATEGY_INDEX:
                return removeBrickStrategy;
            case ADD_PADDLE_STRATEGY_INDEX:
                return new AddPaddleStrategy(removeBrickStrategy, this.imageReader, this.inputListener,
                        this.windowDimensions);
            case PUCK_STRATEGY_INDEX:
                return new PuckStrategy(removeBrickStrategy, this.imageReader, this.soundReader);
            case CHANGE_CAMERA_STRATEGY_INDEX:
                return new ChangeCameraStrategy(removeBrickStrategy, this.windowController, this.gameManager);
            case CHANGE_GAME_SPEED_STRATEGY_INDEX:
                return new ChangeGameSpeedStrategy(removeBrickStrategy, this.windowController, this.imageReader);
            default:
                return new RemoveBrickStrategy(this.gameObjectCollection);
        }
    }
}
