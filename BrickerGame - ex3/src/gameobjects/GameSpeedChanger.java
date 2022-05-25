package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.ChangeGameSpeedStrategy;
import src.brick_strategies.CollisionStrategy;

public class GameSpeedChanger extends StatusChanger{

    private final int typeOfChanger;
    private final CollisionStrategy collisionStrategy;
    private boolean changeAlready = false;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param typeOfChanger quicker or slower
     */
    public GameSpeedChanger(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int typeOfChanger,
                            CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable, collisionStrategy);
        this.typeOfChanger = typeOfChanger;
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * change the speed of the game
     * @param other the object that collided with our speedChanger
     * @param collision Stores information regarding a given collision between two GameObjects
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (changeAlready){
            return;
        }
        // use strategy when collide only with a paddle
        if (other instanceof Paddle){
            ChangeGameSpeedStrategy changeGameSpeedStrategy = (ChangeGameSpeedStrategy)this.collisionStrategy;
            changeGameSpeedStrategy.changeGameSpeed(this.typeOfChanger, this);
            changeAlready = true;
        }
    }
}
