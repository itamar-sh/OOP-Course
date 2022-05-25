package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.*;


/**
 * class used to create the terrain of the game
 */
import java.awt.*;

public class Terrain {
    private final GameObjectCollection gameObjects;
    private final int groundLayer;
    private final NoiseGenerator noiseGenerator;
    private final Vector2 windowDimensions;
    float groundHeightAtX0;
    float ONE_THIRD =  (float)((1.0)/(5.0));
    private static final String innerBlockTag = "block";
    private static final String groundTag = "ground";
    private static final Color BASE_GROUND_COLOR = new Color(200, 123, 74);
    private static final int DIVIDER = 2;
    private static final int X_MINIMIZER = 500;
    private static final float CONSTANT_Y = 10f;
    private static final float CONSTANT_Z = 0f;
    private static final int BLOCKS_BELOW_GROUND = -Block.SIZE/3;


//    private static final int NUM_OF_COLORS = 4;

    /**
     * Constructor
     * @param gameObjects  object that control all the objects in the game
     * @param groundLayer number of the layer of the ground.
     * @param windowDimensions dimensions of the screen
     * @param seed the seed used to generate randomness for the game
     */
    public Terrain(GameObjectCollection gameObjects,
                   int groundLayer, Vector2 windowDimensions,
                   int seed){
        this.windowDimensions = windowDimensions;
        groundHeightAtX0 = windowDimensions.y()*ONE_THIRD;
        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        noiseGenerator = new NoiseGenerator(seed);
//        initializeBlocksArray();
    }

    /**
     * caclutes the height of the ground at the x that was received using a perlin noise function
     * @param x the x value to check
     * @return the height of the ground at the x  that was received
     */
    public float groundHeightAt(float x){
        float a = (float) (groundHeightAtX0 + ((noiseGenerator.improvedNoise((x/X_MINIMIZER), CONSTANT_Y,
                CONSTANT_Z))*
                        windowDimensions.y()));
        if (a < DIVIDER*Block.SIZE)
            a = DIVIDER*Block.SIZE;
        if (a > windowDimensions.y()/DIVIDER) a = windowDimensions.y()/DIVIDER;
        return (int) (Math.floor(a / Block.SIZE) * Block.SIZE);
    }

    /**
     *
     * @param minX the minimal x that the ground generated should cover
     * @param maxX the maximal x that the ground generated should cover
     */
    public void createInRange(int minX, int maxX){
        if (minX == maxX) return;
        int numberOfBlocksInHeight;
        int numberOfBlocksInWidth = (int) Math. ceil(((float)(maxX - minX)/Block.SIZE));
        int startPosX = maxX - numberOfBlocksInWidth*Block.SIZE;
        for (int i = 0; i < numberOfBlocksInWidth; i++) {
            int startPosY = (int) groundHeightAt(startPosX);
            numberOfBlocksInHeight = startPosY/Block.SIZE;
            for (int j = numberOfBlocksInHeight; j > BLOCKS_BELOW_GROUND; j--) {
                Vector2 pos = new Vector2(startPosX,windowDimensions.y() - startPosY);
                if(j < numberOfBlocksInHeight - 1)
                    createSingleBlock(pos,groundLayer + 1,groundTag);
                else createSingleBlock(pos,groundLayer,innerBlockTag);
                startPosY -= Block.SIZE;
            }
            startPosX += Block.SIZE;

        }

    }

    // creates a single ground block
    private void createSingleBlock(Vector2 pos, int layer, String tag){
        Block block = new Block(pos,new RectangleRenderable
                (ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
        gameObjects.addGameObject(block, layer);
        block.setTag(tag);
    }


}
