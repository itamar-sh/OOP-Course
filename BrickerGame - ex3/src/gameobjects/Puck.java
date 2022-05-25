package src.gameobjects;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Puck extends Ball{

    private static final int RANDOM_NUM_TO_PICK_FROM = 18;
    private static final int BASE_SPEED = 100;
    private static final int HELP_CALC = 2; // meant to help calculate number between 1 or -1

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     * @param collisionSound sound made when collision happens
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        int randomSpeed = shuffleRandomSpeed();
        int plusOrMinus = plusOrMinusRandom();
        this.setVelocity(new Vector2((plusOrMinus)*(randomSpeed), randomSpeed));
    }

    private int plusOrMinusRandom(){
        Random random = new Random();
        int plusOrMinus = random.nextInt(HELP_CALC);
        return 1-HELP_CALC*plusOrMinus;
    }

    private int shuffleRandomSpeed(){
        Random random = new Random();
        int randomNum = random.nextInt(RANDOM_NUM_TO_PICK_FROM);
        return randomNum*randomNum+BASE_SPEED;
    }
}
