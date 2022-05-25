package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class MockPaddle extends Paddle{
    public static boolean isInstantiated = false;
    private final GameObjectCollection gameObjectCollection;
    private int numCollisionsToDisappear;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object. can be null.
     * @param inputListener       Contains a single method: isKeyPressed, which returns whether if a key is pressed
     * @param windowsDimensions   dimensions of window
     * @param minDistanceFromEdge  minimum distance from the edges of windows
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,
                      Vector2 windowsDimensions, GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge, int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowsDimensions, minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        MockPaddle.isInstantiated = true;
    }

    /**
     * when "numCollisionsToDisappear" is zero its mean the pudlle hit 4 object so it will be removed.
     * @param other the object that collided with our paddle.
     * @param collision Stores information regarding a given collision between two GameObjects
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.numCollisionsToDisappear -= 1;
        if (this.numCollisionsToDisappear <= 0) {
            MockPaddle.isInstantiated = false;
        }
    }
}
