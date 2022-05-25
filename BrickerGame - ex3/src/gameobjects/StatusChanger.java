package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.CollisionStrategy;

/**
 * class represents an object that could be collected by the puddle and change the status of the game
 */
public class StatusChanger extends GameObject {
    private static final int BASIC_SPEED = 50;
    private final CollisionStrategy collisionStrategy;


    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public StatusChanger(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        setVelocity(new Vector2(0, BASIC_SPEED));
    }

}
