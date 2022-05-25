package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * class represents a Paddle (player).
 * Not supposed to collide with Layer.background objects. (only with the ball)
 */
public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 400;
    private final UserInputListener inputListener;
    private final Vector2 windowsDimensions;
    private final int minDistanceFromEdge;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                            a given key is currently pressed by the user or not. See its documentation.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowsDimensions, int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowsDimensions = windowsDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    /**
     * move according to the keys.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        // check end of window from left + press left
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && this.getTopLeftCorner().x() > this.minDistanceFromEdge) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            // check end of window from right
            if(this.getTopLeftCorner().x() + this.getDimensions().x() +
                    this.minDistanceFromEdge < this.windowsDimensions.x()){
                movementDir = movementDir.add(Vector2.RIGHT);
            }
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
