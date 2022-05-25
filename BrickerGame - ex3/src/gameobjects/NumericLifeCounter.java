package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class NumericLifeCounter extends GameObject {

    private final Counter livesCounter;
    private final TextRenderable renderable;

    /**
     * constructor
     * @param livesCounter  counter of lives
     * @param topLeftCorner  location for the number
     * @param dimensions  dimensions of the number to display
     * @param gameObjectCollection  object that control all the objects in the game
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        this.renderable = new TextRenderable(Integer.toString(livesCounter.value()));
        GameObject lifeNumber = new GameObject(topLeftCorner, dimensions, renderable);
        gameObjectCollection.addGameObject(lifeNumber, Layer.BACKGROUND);
    }

    /**
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     */
    @Override
    public void update(float deltaTime) {
        this.renderable.setString(Integer.toString(livesCounter.value()));
        super.update(deltaTime);
    }
}
