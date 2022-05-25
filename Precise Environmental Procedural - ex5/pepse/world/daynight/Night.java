package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * This class control the brightness of the game. By controlling object that has transparency and make the screen dark
 * and light by time. (Object in the size of the Screen).
 */
public class Night {
    private static final Color BASIC_BLACK_COLOR = Color.decode("#000000");
    private static final Float MIDNIGHT_OPACITY = 0.5f;
    private static final String NIGHT_TAG = "night";
    private static final float INITIAL_TRANSITION_VALUE = 0;
    private static final int FACTOR_TO_DIVIDE_FOR_CYCLE_LENGTH = 2;
    /**
     *
     * @param gameObjects  object that control all the objects in the game
     * @param layer  number of the layer of the game.
     * @param windowDimensions dimensions of the screen
     * @param cycleLength the egnth of each round around the center of the screen.
     * @return return the created Night object.
     */
    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer, Vector2 windowDimensions, float cycleLength){
        GameObject night = new GameObject(Vector2.ZERO,windowDimensions,new RectangleRenderable(BASIC_BLACK_COLOR));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(night, layer);
        night.setTag(NIGHT_TAG);
        // make transition
        Transition transitioner = new Transition<>(
                night, // the game object being changed
                night.renderer()::setOpaqueness, // the method to call
                INITIAL_TRANSITION_VALUE, // initial transition value
                MIDNIGHT_OPACITY, // final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT, // use a cubic interpolator
                cycleLength/FACTOR_TO_DIVIDE_FOR_CYCLE_LENGTH, // transtion fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null); // nothing further to execute upon reaching final value
        return night;

    }
}
