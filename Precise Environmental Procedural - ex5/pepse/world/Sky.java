package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * class represents the Sky of the game. Basic Rectangle with blue color.
 */
public class Sky {
    private static final String SKY_TAG = "sky";
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     *
     * @param gameObjects  object that control all the objects in the game
     * @param skyLayer  number of the layer of the game.
     * @param windowDimensions dimensions of the screen
     * @return return the created Sky object.
     */
    public static GameObject create(GameObjectCollection gameObjects,
                                    Vector2 windowDimensions, int skyLayer){
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(sky, skyLayer);
        sky.setTag(SKY_TAG);
        return sky;

    }
}
