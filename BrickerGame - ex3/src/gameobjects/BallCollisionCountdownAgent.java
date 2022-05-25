package src.gameobjects;

import danogl.GameObject;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * class represents an Agent that control turning of change of camera, following specific object
 */
public class BallCollisionCountdownAgent extends GameObject {

    private final Ball ball;
    private final ChangeCameraStrategy owner;
    private final int ValueToStopCamera;
    private boolean goodToUse;

    /**
     * constructor that keep in the side the num of the collision of the ball via that info it calculates the num of
     * times we want to wait for the ball collisions to stop the camera
     * @param ball  a ball to follow
     * @param owner  the instance of strategy that made the agent
     * @param countDownValue  how many times of hits until we stop the change in the camera
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue){
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.owner = owner;
        this.ValueToStopCamera = ball.getCollisionCount() + countDownValue;
        this.goodToUse = true;
    }

    /**
     * check the calculation that decide if we stop the camera
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (ball.getCollisionCount() > this.ValueToStopCamera && this.goodToUse){
            this.owner.turnOffCameraChange();
            this.goodToUse = false;
        }
    }
}
