package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.Component;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;


/**
 * class that represents a single leaf, the leaf can move on the tree, and can fall from the tree.
 * after a while the leaf disappears and then returns to its original position
 */
public class Leaf extends GameObject{
    private static final float FADEOUT_TIME = 15;
    private final static float ANGLE = 10f;
    private final static int WAIT_TO_REBORN = 5;
    private final static int FALLING_VELOCITY = 60;
    private final static float SHRIEKER = 0.9f;
    private final static float MOVER = 0.5f;
    private static final int MAX_LIFE_TIME = 60;
    private final Random random = new Random();
    private final Vector2 topLeftCorner;
    private final int lifeTime;
    private final Component horizontalTransition;
    private static final float INITIAL_X_VELOCITY = -40f;
    private static final float TIME_FOR_X_TRANSFORM = 2f;
    private static final float X_VELOCITY_MINIMIZER = 0.6f;
    public static final String leafBlockTag = "leaf";
    public static final String fallingLeafBlockTag = "falling";
    private static final int PAD_FOR_LIFE_TIME = 6;
    private static final float FADE_IN_TIME = 0.2f;
    private static final float GRAVITY = 9f;
    private static final float UPPER_V = -1f;


    /**
     *
     * @param topLeftCorner the top left corner position of the leaf
     * @param renderable The renderable representing the object.
     * @param random random objectt used to generate randomness
     * @param dimensions dimensions of the leaf
     */
    public Leaf(Vector2 topLeftCorner, Renderable renderable, Random random, Vector2 dimensions){
        super(topLeftCorner, dimensions, renderable);
        lifeTime = random.nextInt(MAX_LIFE_TIME)+PAD_FOR_LIFE_TIME;
        this.topLeftCorner = topLeftCorner;
        this.setTag(leafBlockTag);
        this.renderer().setRenderableAngle(ANGLE);
        horizontalTransition = new Transition<>(
                this, // the game object being changed
                this.transform()::setVelocityX,
                // the method to call
                INITIAL_X_VELOCITY, // initial transition value
                -INITIAL_X_VELOCITY, // final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                TIME_FOR_X_TRANSFORM,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,null);
        this.removeComponent(horizontalTransition);
        new ScheduledTask(
                this,
                lifeTime,
                false,
                this::lifecycle);
    }

    /**
     * determines if the leaf should collide with other object. used in order to avoid collision detection for
     * leaves that haven't fallen yet
     * @param other other object that the leaf could collide with
     * @return if the leaf is on the tree no collision should be detected, if the leaf has fallen then the
     * super function shouldCollideWith supplies the return value.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if(this.getTag().equals(leafBlockTag))
            return false;
        return super.shouldCollideWith(other);
    }

    //stats the cycle of the falling leaf
    private void lifecycle(){
        this.transform().setVelocityY(FALLING_VELOCITY);
        this.setTag(fallingLeafBlockTag);
        this.addComponent(horizontalTransition);
        renderer().fadeOut(FADEOUT_TIME, this::callRePosition);

    }
    //preforms wait time and calls rePosition
    private void callRePosition(){
        int waitTime = random.nextInt(WAIT_TO_REBORN);
        this.removeComponent(horizontalTransition);
        new ScheduledTask(
                this,
                waitTime,
                false,
                this::rePosition);
    }

    // reposition the leaf on the tree
    private void rePosition(){
        this.setTag(leafBlockTag);
        this.setTopLeftCorner(topLeftCorner);
        renderer().fadeIn(FADE_IN_TIME);
        this.setVelocity(Vector2.ZERO);
        this.transform().setAccelerationY(0);
        new ScheduledTask(
                this,
                lifeTime,
                false,
                this::lifecycle);

    }


    /**
     * handels collisions of the leaf, at the moments of the collision
     * @param other other object collided with
     * @param collision collision object
     */
    @Override
    public void onCollisionStay(GameObject other, Collision collision) {
        super.onCollisionStay(other, collision);
        this.removeComponent(horizontalTransition);
        this.transform().setVelocityX(this.getVelocity().flipped(collision.getNormal()).x()*
                X_VELOCITY_MINIMIZER);
        this.transform().setAccelerationY(GRAVITY);
        this.transform().setVelocityY(-UPPER_V);
         float newX = this.getCenter().x();
         if(this.getCenter().x() < other.getCenter().x()) newX-=MOVER;
         else newX+=MOVER;
        float newY = this.getCenter().y();
        if(this.getCenter().y() < other.getCenter().y()) newY-=MOVER;
        else newY+=MOVER;
        this.setCenter(new Vector2(newX,newY));

    }

    /**
     * handels exit of the leaf from the collision
     * @param other other object collided with
     */
    @Override
    public void onCollisionExit(GameObject other) {
        super.onCollisionExit(other);
        this.transform().setAccelerationY(GRAVITY);
    }

    /**
     * preforms action of moving the leaf, simulating movement caused by wind
     */
    public void windMover(){
         new Transition<>(
                this, // the game object being changed
                this.renderer()::setRenderableAngle,
                // the method to call
                this.renderer().getRenderableAngle(), // initial transition value
                 -this.renderer().getRenderableAngle(), // final transition value
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                TIME_FOR_X_TRANSFORM,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,null);
        new Transition<>(
                this, // the game object being changed
                this::setDimensions,
                // the method to call
                this.getDimensions(), // initial transition value
                this.getDimensions().mult(SHRIEKER), // final transition value
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                TIME_FOR_X_TRANSFORM,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,null);

    }


}
