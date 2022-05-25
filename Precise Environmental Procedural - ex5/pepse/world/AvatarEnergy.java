package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * This class represents an Energy. It has two main functionality.
 * Handle the Energy value and display it.
 */
public class AvatarEnergy extends GameObject {
    private static final float ENERGY_START = 50;
    private static final String ENERGY_PREFIX_DISPLAY = "Energy: ";
    private static final String FONT_NAME = "David";

    private static final float MIN_ENERGY = 0;
    private static final int MAX_ENERGY = 100;
    private float energyCounter;
    private final TextRenderable renderable;
    private static final int CUT_LAST_DIGIT_FACTOR = 10;


    /**
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     *
     * @param dimensions    Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case
     */
    public AvatarEnergy(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.energyCounter = ENERGY_START;
        this.renderable = renderable;
    }

    /**
     *
     * @param gameObjects  object that control all the objects in the game
     * @param layer  number of the layer of the game.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions  dimensions of the number to display
     * @return return the created EnergyAvatar object.
     */
    public static AvatarEnergy create(GameObjectCollection gameObjects,
                                      int layer, Vector2 topLeftCorner, Vector2 dimensions){
        TextRenderable textRenderable = new TextRenderable(Float.toString(ENERGY_START),FONT_NAME,
                true,true);
        textRenderable.setColor(Color.RED);
        AvatarEnergy avatarEnergy = new AvatarEnergy(topLeftCorner, dimensions,
                textRenderable);
        gameObjects.addGameObject(avatarEnergy, layer);
        return avatarEnergy;
    }

    /**
     * Method that add a certain value (can be negative) to the Energy value.
     * If the energy is getting lower than the min value. change it to be the min value.
     * @param addition value to add.
     */
    public void addEnergy(float addition){
        this.energyCounter += addition;
        if (this.energyCounter <= MIN_ENERGY){
            this.energyCounter = MIN_ENERGY;
        }
        if (this.energyCounter >= MAX_ENERGY){
            this.energyCounter = MAX_ENERGY;
        }
    }

    /**
     * getter of the value of the Energy.
     * @return return the current value of the energy
     */
    public float getEnergyValue(){
        return this.energyCounter;
    }

    /**
     * Render the Energy accordingly to the current value.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     */
    @Override
    public void update(float deltaTime) {
        this.renderEnergy();
        super.update(deltaTime);
    }

    /**
     * render the energy according the value of the energy.
     */
    private void renderEnergy(){
        int energyToDisplay = ((int)this.energyCounter/CUT_LAST_DIGIT_FACTOR)*CUT_LAST_DIGIT_FACTOR;
        this.renderable.setString(ENERGY_PREFIX_DISPLAY + energyToDisplay);
    }
}
