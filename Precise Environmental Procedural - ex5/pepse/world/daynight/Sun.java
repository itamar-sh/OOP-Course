package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * This class represents the sun. basically yellow circle moving around the center of the screen.
 */
public class Sun {
    private static final int FACTOR_TO_DIVIDE_FOR_Y_START_LOCATION = 8;
    private static final int FACTOR_TO_DIVIDE_FOR_X_START_LOCATION = 2;
    private static final int FACTOR_TO_DIVIDE_FOR_SIZE = 4;
    private static final String SUN_TAG = "sun";
    private static final float INITIAL_TRANSITION_VALUE = 0;
    // calculate as the perimeter of a circle
    private static final float FINAL_TRANSITION_VALUE = (float)(2*Math.PI);
    private static final float FACTOR_TO_MULT_FOR_RANDIUS = 4.5f/8;
    private static final double DARK_START_ANGLE = Math.PI/2;
    private static final double DARK_FINAL_ANGLE = Math.PI*1.5;
    private static final int FACTOR_TO_DIVIDE_FOR_FINDING_CENTER = 2;
    private static final float FACTOR_TO_MULT_FOR_ELLIPSE = 2f;


    /**
     *  Create a Sun object.
     * @param gameObjects  object that control all the objects in the game
     * @param layer  number of the layer of the game.
     * @param windowDimensions dimensions of the screen
     * @param cycleLength the egnth of each round around the center of the screen.
     * @return return the created Sun object.
     */
    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            Vector2 windowDimensions,
            float cycleLength){
        // take care of the sun location
        Vector2 sunStartPosition = new Vector2(windowDimensions.x()/FACTOR_TO_DIVIDE_FOR_Y_START_LOCATION,
                windowDimensions.y()/FACTOR_TO_DIVIDE_FOR_X_START_LOCATION);
        Vector2 sunSize = new Vector2(windowDimensions.y()/FACTOR_TO_DIVIDE_FOR_SIZE,
                windowDimensions.y()/FACTOR_TO_DIVIDE_FOR_SIZE);
        // sun creation
        GameObject sun = new GameObject(Vector2.ZERO,sunSize,new OvalRenderable(Color.YELLOW));
        sun.setCenter(sunStartPosition);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(sun, layer);
        sun.setTag(SUN_TAG);
        // make transition
        Transition<Float> transitioner = new Transition<>(
                sun, // the game object being changed
                angle -> Sun.setLocationByAngle(sun, angle, windowDimensions) , // the method to call
                INITIAL_TRANSITION_VALUE, // initial transition value
                FINAL_TRANSITION_VALUE, // final transition value
                Transition.LINEAR_INTERPOLATOR_FLOAT, // use a cubic interpolator
                cycleLength, // transition fully over half a day
                Transition.TransitionType.TRANSITION_LOOP,
                null); // nothing further to execute upon reaching final value
        return sun;
    }

    /**
     * Function to manipulate sun location while moving.
     * @param sun Sun object to move
     * @param angle angle for the sun to be
     * @param windowDimensions dimensions of the screen
     */
    public static void setLocationByAngle(GameObject sun, float angle, Vector2 windowDimensions){
        float Radius = FACTOR_TO_MULT_FOR_RANDIUS*windowDimensions.y();
        float center_window_x = windowDimensions.x()/FACTOR_TO_DIVIDE_FOR_FINDING_CENTER;
        float center_window_y = windowDimensions.y()/FACTOR_TO_DIVIDE_FOR_FINDING_CENTER;
        // if we after down and before sunrise than we want the sun to not been seen.(Determined by the angle).
        if (angle > DARK_START_ANGLE && angle < DARK_FINAL_ANGLE){
            Radius += Radius;
        }
        // the calculation of the Radius plus the location of the center of the Cycle.
        Vector2 sunCenter = new Vector2(center_window_x+
                FACTOR_TO_MULT_FOR_ELLIPSE*Radius*(float)Math.sin(angle),
                center_window_y+
                        Radius*-(float)Math.cos(angle));
        sun.setCenter(sunCenter);
    }

}