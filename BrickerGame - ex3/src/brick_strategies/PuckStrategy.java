package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

/**
 * a class deals with a strategy of brick that add 3 new balls.
 */
public class PuckStrategy extends RemoveBrickStrategyDecorator{
    private static final String PUCK_IMAGE = "assets/mockBall.png";
    private static final String SOUND_FILE = "assets/blop_cut_silenced.wav";
    private static final int SHUFFLE_PLACE_FACTOR = 10;
    private static final int DIVIDE_BY_FOR_SIZE = 3;
    private static final int NUM_OF_PUCKS = 3;
    private final CollisionStrategy toBeDecorated;
    private final Renderable packImage;
    private final Sound collisionSound;

    /**
     *
     * @param toBeDecorated a classic strategy that we add a new strategy to
     * @param imageReader object that can make an image to renderable object
     * @param soundReader object that can make an audio file to Sound object
     */
    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                        danogl.gui.SoundReader soundReader){
        super(toBeDecorated);

        this.toBeDecorated = toBeDecorated;
        this.packImage =
                imageReader.readImage(PUCK_IMAGE, true);
        this.collisionSound = soundReader.readSound(SOUND_FILE);
    }

    /**
     * when called we will make 3 new Puck object and add them to the collection of objects of the game
     * @param thisObj a brick object that has this strategy
     * @param otherObj a ball object that collide with our brick
     * @param counter counter of bricks
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        for (int i = 0; i < NUM_OF_PUCKS; i++) {
            Vector2 location = new Vector2(thisObj.getCenter().x() - i*SHUFFLE_PLACE_FACTOR,
                    thisObj.getCenter().y() + i*SHUFFLE_PLACE_FACTOR);
            Vector2 length = new Vector2(thisObj.getDimensions().x()/DIVIDE_BY_FOR_SIZE,
                    thisObj.getDimensions().x()/DIVIDE_BY_FOR_SIZE);
            Puck puck = new Puck(location, length, this.packImage,
                    this.collisionSound);
            this.toBeDecorated.getGameObjectCollection().addGameObject(puck);
        }
    }


}
