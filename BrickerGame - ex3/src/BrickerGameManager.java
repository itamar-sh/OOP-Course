package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.CollisionStrategy;
import src.gameobjects.*;

import java.util.Iterator;
import java.util.Random;

/**
 * Class represents a kind of games.
 * Control all the object and the game flow.
 * All the methods that create objects start with "create"
 * run method simply engine everything.
 * and in the initializeGame we gave tha calls for the creators methods.
 */
public class BrickerGameManager extends GameManager{

    // sizes of paddles, ball, borders and bricks
    public static final float BORDER_WIDTH = 10;

    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 20;
    private static final float BALL_SPEED = 225;
    private static final int BRICK_HEIGHT = 15;
    private static final int NUM_OF_BRICK_IN_LINE = 8;
    private static final int NUM_OF_BRICKS_LINES = 5;
    private static final int SPACE_BETWEEN_BRICKS = 1;
    private static final int SPACE_BETWEEN_BORDERS_AND_BRICKS = 10;
    private static final int MIN_DISTANCE_FROM_EDGE = 10;
    private static final int MIN_DISTANCE_FROM_BOTTOM = 30;
    private static final int HEART_LOCATION = 40;
    private static final int LIFE_SIZE = 20;
    private static final int NUM_LIFE_X_LOC = 40;
    private static final int NUM_LIFE_Y_LOC = 70;
    // basic objects
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    // counter of lives
    private final Counter bricksCounter;
    private static final int NUM_OF_LIVES = 4;
    private final Counter livesCounter;
    // flag of game ended
    private boolean isGameEnded = false;
    // general constants
    private static final int FRAME_RETE = 80;
    private static final float BALL_POSITION = 0.5f;
    // images and sounds
    private static final String BACKGROUND_IMAGE = "assets/DARK_BG2_small.jpeg";
    private static final String BALL_IMAGE = "assets/ball.png";
    private static final String BALL_SOUND = "assets/blop_cut_silenced.wav";
    private static final String PADDLE_IMAGE = "assets/paddle.png";
    private static final String LIFE_IMAGE = "assets/heart.png";
    private static final String BRICK_IMAGE = "assets/brick.png";



    /**
     * simple constructor - make the board (empty).
     * The GUI will call InitializeGame that will build everything. (for every game).
     * @param windowsTitle - name of the window
     * @param windowsDimensions - board size.
     */
    public BrickerGameManager(String windowsTitle, Vector2 windowsDimensions) {
        super(windowsTitle, windowsDimensions);
        this.bricksCounter = new Counter(0);
        this.livesCounter = new Counter(NUM_OF_LIVES);
    }

    /**
     * The methods that calls all the create methods. we make here all the object,
     * including the background and the windows sizes
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.windowController = windowController;
        //initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController.setTargetFramerate(FRAME_RETE);
        this.windowDimensions = windowController.getWindowDimensions();

        // create background
        createBackground(imageReader, this.windowDimensions);

        // create bricks
        createBricks(imageReader, soundReader, this.windowDimensions, inputListener);

        //create ball
        createBall(imageReader, soundReader);

        //create paddles
        createPaddle(imageReader, inputListener, windowDimensions);

        //create borders
        createBorders(windowDimensions);



        // create graphical lives
        createGraphicalLifeCounter(imageReader, windowDimensions);

        // create numerical lives
        createNumericalLifeCounter(windowDimensions);
    }

    /**
     * continue the game and check if is ended.
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // self implement of "garbage collector". we seek for specific object with unique behaviour
        Iterator<GameObject> objects = this.gameObjects().iterator();
        while (objects.hasNext()){
            GameObject tempObject = objects.next();
            if (tempObject instanceof MockPaddle){
                MockPaddle tempObject2 = (MockPaddle)tempObject;
                if (!MockPaddle.isInstantiated){
                    this.gameObjects().removeGameObject(tempObject);
                }
            }
            if (tempObject instanceof Brick){
                this.isGameEnded = false;
            }

        }
        if (this.getCamera() != null && this.getCamera().getCenter().y() > this.windowDimensions.y()){
            this.setCamera(null);
        }
        checkForGameEnd();
    }

    /**
     * set ball to the center of the board
     * @param ball a ball object
     */
    public void repositionBall(GameObject ball){
        ball.setCenter(windowDimensions.mult(BALL_POSITION));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean())
            ballVelX *= -1;
        if(rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * check if the game is ended. Check with the user if he wants to continue - if yes than restart the gae.
     */
    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if(this.bricksCounter.value() <= 0) {
            if (this.isGameEnded){
                prompt = "You win!";
            }
            this.isGameEnded = true;
        }
        if(ballHeight > windowDimensions.y()) {
            this.livesCounter.decrement(); // decrease life - the counters life will update accordingly
            if (this.livesCounter.value() <= 0){
                prompt = "You Lose!";
            } else {
                // lose one life
                repositionBall(ball);
            }
        }
        if(!prompt.isEmpty()) {
            prompt += " Play again?";
            if(windowController.openYesNoDialog(prompt)) {
                // reset board
                windowController.resetGame();
                // reset lives counter
                this.livesCounter.reset();
                this.livesCounter.increaseBy(NUM_OF_LIVES);
                // reset bricks counter
                this.bricksCounter.reset();
            }
            else
                windowController.closeWindow();
        }
    }

    /**
     * Create background for the board
     * @param imageReader object that can read a picture and make a renderable object from it.
     * @param windowDimensions dimensions of the board
     */
    private void createBackground(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable backgroundImage = imageReader.readImage(
                BACKGROUND_IMAGE, false);
        GameObject background = new GameObject(Vector2.ZERO,  windowController.getWindowDimensions(), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

    }

    /**
     * Create ball in the middle of the board anf give him speed and direction
     * @param imageReader object that can read a picture and make a renderable object from it.
     * @param soundReader object that can read a sound file and make a Sound object from it.
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage =
                imageReader.readImage(BALL_IMAGE, true);
        Sound collisionSound = soundReader.readSound(BALL_SOUND);
        ball = new Ball(
                windowDimensions.mult(0.5f), new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);

        gameObjects().addGameObject(ball);
        repositionBall(ball); // put in the middle and give him speed and direction
    }

    /**
     * create paddle tat the user can control
     * @param imageReader object that can read a picture and make a renderable object from it.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowDimensions sizes of the board.
     */
    private void createPaddle(ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage(
                PADDLE_IMAGE, false);
        GameObject paddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                windowDimensions,
                MIN_DISTANCE_FROM_EDGE);

        paddle.setCenter(
                new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-MIN_DISTANCE_FROM_BOTTOM));
        gameObjects().addGameObject(paddle);
    }

    /**
     * create simple objects represents the borders.
     * @param windowDimensions sizes of the board.
     */
    private void createBorders(Vector2 windowDimensions) {
        // left border
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        null)
        );
        //right border
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x()-BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        null)
        );
        // ceil border
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(windowDimensions.x(), BORDER_WIDTH),
                        null)
        );
    }

    /**
     * Make bricks, make a counter of them to know when the game is ended.
     * @param imageReader object that can read a picture and make a Renderable object from it.
     * @param soundReader object that can read an audio file and make a Sound object from it.
     * @param windowDimensions sizes of the board.
     */
    private void createBricks(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions,
                              UserInputListener inputListener) {
        Renderable brickImage = imageReader.readImage(BRICK_IMAGE, false);
        CollisionStrategy[] collisionStrategy = createCollisionStrategies(
                imageReader, soundReader,
                inputListener);

        // how much space we need in total: twice the space around border plus the space between bricks
        // plus twice the border width
        float spaces = SPACE_BETWEEN_BORDERS_AND_BRICKS + SPACE_BETWEEN_BORDERS_AND_BRICKS
                + BORDER_WIDTH + BORDER_WIDTH +
                (NUM_OF_BRICK_IN_LINE - 1)*SPACE_BETWEEN_BRICKS;
        float brickWidth = (windowDimensions.x() - spaces) / NUM_OF_BRICK_IN_LINE;
        // cursorY and cursorX are the coordinates of the next brick.
        float cursorY = 0; // height of line
        for (int i = 0; i < NUM_OF_BRICKS_LINES ; i++){
            float cursorX = SPACE_BETWEEN_BORDERS_AND_BRICKS + BORDER_WIDTH;
            cursorY += BRICK_HEIGHT + 1; // y coordinate of next brick - based on the current coordinate + brick height
            for (int j = 0; j < NUM_OF_BRICK_IN_LINE ; j++){
                GameObject brick = new Brick(
                        new Vector2(cursorX, cursorY),
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage, collisionStrategy[i*NUM_OF_BRICK_IN_LINE + j], this.bricksCounter);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                cursorX += brickWidth + 1; // x coordinate of next brick - based on the current coordinate + brick len
            }

        }
        this.bricksCounter.increaseBy(NUM_OF_BRICK_IN_LINE * NUM_OF_BRICKS_LINES);
    }

    private CollisionStrategy[] createCollisionStrategies(ImageReader imageReader, SoundReader collisionSound,
                                                          UserInputListener inputListener) {
        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjects(), this,
                imageReader, collisionSound, inputListener, this.windowController, this.windowDimensions);
        CollisionStrategy[] collisionStrategy = new CollisionStrategy[NUM_OF_BRICKS_LINES*NUM_OF_BRICK_IN_LINE];
        for (int i = 0; i < NUM_OF_BRICKS_LINES*NUM_OF_BRICK_IN_LINE; i++) {
            collisionStrategy[i] = brickStrategyFactory.getStrategy();
        }
        return collisionStrategy;
    }

    /**
     * Make counter that represents the remaining lives. In hearts symbols.
     * @param imageReader object that can read a picture and make a renderable object from it.
     * @param windowDimensions sizes of the board.
     */
    private void createGraphicalLifeCounter(ImageReader imageReader, Vector2 windowDimensions){
        Renderable lifeImage = imageReader.readImage(
                LIFE_IMAGE, true);
        Vector2 heartsLoaction = new Vector2(HEART_LOCATION, windowDimensions.y() -HEART_LOCATION);
        gameObjects().addGameObject(
                new GraphicLifeCounter(
                        heartsLoaction,
                        new Vector2(LIFE_SIZE, LIFE_SIZE),livesCounter,
                        lifeImage, gameObjects(), NUM_OF_LIVES)
                , Layer.BACKGROUND
        );
    }

    /**
     * Make counter that represents the remaining lives. In a black number on the screen.
     * @param windowDimensions sizes of the board.
     */
    private void createNumericalLifeCounter(Vector2 windowDimensions){
        Vector2 lifeNumberLoaction = new Vector2(NUM_LIFE_X_LOC, windowDimensions.y() -NUM_LIFE_Y_LOC);
        gameObjects().addGameObject(
                new NumericLifeCounter(
                        livesCounter,
                        lifeNumberLoaction,
                        new Vector2(LIFE_SIZE, LIFE_SIZE),
                        gameObjects())
                ,Layer.BACKGROUND
        );
    }

    public static void main(String[] args) {
        BrickerGameManager g =new BrickerGameManager("BouncingBall", new Vector2(700, 500));
        g.run();

    }
}
