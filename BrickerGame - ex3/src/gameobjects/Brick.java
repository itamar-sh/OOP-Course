package src.gameobjects;

import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * class represents a single brick.
 * when there os collision with a ball - should disappear
 */
public class Brick extends GameObject {

    private final CollisionStrategy collisionStrategy;
    private final Counter counter;
    private boolean goingToBeDelted = false;

    /**
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param collisionStrategy strategy of the brick when collide with a ball
     * @param counter  counter of how many bricks have been broken
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy collisionStrategy,
                 Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.counter = counter;
    }

    /**
     * After the collision - the brick disappear.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        // check edge-case that the function called many times on same collision
        if (goingToBeDelted){
            return;
        }
        goingToBeDelted = true;
        // active strategy
        this.collisionStrategy.onCollision(this, other, this.counter);

    }
}
