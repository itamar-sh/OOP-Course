package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * class represents the Halo around the sun.
 */
public class SunHalo {
    private static final float FACTOR_TO_MULT_FOR_SIZE = 2;
    private static final String SUN_HALO_TAG = "sunHalo";

    /**
     * Create new SunHalo
     * @param gameObjects  object that control all the objects in the game
     * @param layer  number of the layer of the game.
     * @param sun the sun object that we want to give him an Halo.
     * @param color color of the Halo.
     * @return the sunHalo object.
     */
    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer, GameObject sun, Color color){
        Vector2 sunHaloSize = new Vector2(FACTOR_TO_MULT_FOR_SIZE*sun.getDimensions().x(),
                FACTOR_TO_MULT_FOR_SIZE*sun.getDimensions().y());
        GameObject sunHalo = new GameObject(Vector2.ZERO,sunHaloSize,new OvalRenderable(color));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(sunHalo, layer);
        sunHalo.setTag(SUN_HALO_TAG);
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        return sunHalo;
    }

}
