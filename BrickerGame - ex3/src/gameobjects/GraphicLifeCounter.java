package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {

    private final Counter livesCounter;
    private final GameObjectCollection gameObjectCollection;
    private final int numOfLives;
    private final GameObject[] lives;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Counter livesCounter, Renderable renderable,
                              GameObjectCollection gameObjectCollection, int numOfLives) {
        super(topLeftCorner, dimensions, renderable);
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        this.numOfLives = numOfLives;
        this.lives = new GameObject[numOfLives];
        // stores array of lives (GameObject) to display on the screen
        for (int i = 1 ; i < this.numOfLives ; i++){
            this.lives[i] = new GameObject(new Vector2(topLeftCorner.x() + i * dimensions.x() + 1,
                    topLeftCorner.y()), dimensions, renderable);
            this.gameObjectCollection.addGameObject(this.lives[i], Layer.BACKGROUND);
        }
    }

    /**
     * stores array of lives (GameObject) when someone is null we need to remove him.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // stores array of lives (GameObject) when someone is null we need to remove him
        for (int i = this.livesCounter.value() ; i < this.numOfLives ; i++) {
            if (lives[i] != null){
                this.gameObjectCollection.removeGameObject(lives[i], Layer.BACKGROUND);
            }
        }

    }
}
