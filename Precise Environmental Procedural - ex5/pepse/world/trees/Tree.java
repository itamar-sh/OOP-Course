package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

/**
 * class used to create the trees of the game
 */

public class Tree {
    private final GameObjectCollection gameObjects;
    private final int treeLayer;
    private final int leafLayer;
    private final Vector2 windowDimensions;
    private final Function<Float, Float> getHeight;
    private static final String treeBlockTag = "tree";
    private final int seed;
    private static final int NUM_OF_LEAVES = 3;
    private static final Color LEAF_BASE_COLOR = new Color(50, 200, 30);
    private static final Color TREE_BASE_COLOR = new Color
            (110, 30, 10);
    //color deltas
    private static final int TREE_COLOR_DELTA = 30;
    private static final int LEAF_COLOR_DELTA = 50;
    //private static int PADDING_LEAVES = (int) (Block.SIZE * 0.9f);

    private static final int WAIT_TIME_HELPER = 3;
    //random constants
    private static final int RANDOM_UPPER_BOUND = 12;
    private static final int RANDOM_BIGGER_COND = 10;
    private static final int HEIGHT_RANDOM_UPPER_BOUND = 4;
    private static final int HEIGHT_RANDOM_PAD = 6;
    private static final int LEAF_RANDOM_UPPER_BOUND = 12;
    private static final int LEAF_RANDOM_BIGGER_COND = 2;
    private static final int LEAD_RANDOM_PAD = 5;

    /**
     * Constructor
     * @param gameObjects object that control all the objects in the game
     * @param treeLayer number of the layer of the trees blocks.
     * @param leafLayer number of the layer of the leaf blocks.
     * @param windowDimensions dimensions of the screen
     * @param getHeight function that recives a float representing the x location, and returns the height
     *                   that the first block of the planted tree should begin at
     * @param seed the seed used to generate randomness for the game
     */
    public Tree(GameObjectCollection gameObjects,
                int treeLayer, int leafLayer, Vector2 windowDimensions,
                Function<Float,Float> getHeight,int seed){
        this.gameObjects = gameObjects;
        this.treeLayer = treeLayer;
        this.leafLayer = leafLayer;
        this.windowDimensions = windowDimensions;
        this.getHeight = getHeight;
        this.seed = seed;
    }

    /**
     *
     * @param minX the minimal x that the trees generated should cover
     * @param maxX the maximal x that the trees generated should cover
     */
    public void createInRange(int minX, int maxX) {
        if (minX == maxX) return;
        float curHeightOfGround;
        int curHeightOfTree;
        int curHeightStart;
        Vector2 pos = null;
        int numberOfBlocksInWidth = (int) Math. ceil(((float)(maxX - minX)/ Block.SIZE));
        int startPosX = maxX - numberOfBlocksInWidth*Block.SIZE;
        for (int i = 0; i < numberOfBlocksInWidth; i++) {
            curHeightOfGround = getHeight.apply((float)startPosX);
            Random random = new Random(Objects.hash(startPosX*7,seed));
            RectangleRenderable treeBlockImage = new RectangleRenderable(ColorSupplier.approximateColor(
                    TREE_BASE_COLOR, TREE_COLOR_DELTA));
            if(random.nextInt(RANDOM_UPPER_BOUND) >RANDOM_BIGGER_COND){
                curHeightOfTree = random.nextInt(HEIGHT_RANDOM_UPPER_BOUND) + HEIGHT_RANDOM_PAD;
                curHeightStart = (int) (windowDimensions.y() - curHeightOfGround);
                for (int j = 1; j < curHeightOfTree+1; j++) {
                    pos = new Vector2(startPosX, curHeightStart - j * Block.SIZE);
                    Block treeBlock = new Block(pos, treeBlockImage);
                    gameObjects.addGameObject(treeBlock, treeLayer);
                    treeBlock.setTag(treeBlockTag);
                }
                createLeavesForTree(pos);
            }
            startPosX += Block.SIZE;

        }
    }

    // Create leaves for a tree planted at pos.x()
    private void createLeavesForTree(Vector2 pos){
        Random rand = new Random(Objects.hash((int) pos.x(),seed));
        int numOfLeaves = rand.nextInt(NUM_OF_LEAVES) + LEAD_RANDOM_PAD;
        pos = new Vector2(pos.x()- (float)numOfLeaves/2*Block.SIZE, pos.y());

        for (int i = 0; i < numOfLeaves; i++) {
            for (int j = 0; j < numOfLeaves; j++) {

                if(rand.nextInt(LEAF_RANDOM_UPPER_BOUND)>LEAF_RANDOM_BIGGER_COND &&windowDimensions.y()-
                        getHeight.apply(pos.x()
                        + i*Block.SIZE) > pos.y() - j*Block.SIZE){
                    Vector2 leafPos = new Vector2(pos.x() + i*Block.SIZE, pos.y() - j*Block.SIZE);
                    createSingleLeaf(leafPos,rand);

                }

            }
        }
    }

    // creates a single leaf
    private void createSingleLeaf(Vector2 leafPos,Random rand){
        Leaf leafBlock = new Leaf(leafPos, new RectangleRenderable
                (ColorSupplier.approximateColor(LEAF_BASE_COLOR,LEAF_COLOR_DELTA)),rand,
                Vector2.ONES.mult(Block.SIZE));
        gameObjects.addGameObject(leafBlock, leafLayer);
        new ScheduledTask(
                leafBlock,
                rand.nextFloat()*WAIT_TIME_HELPER,
                false,
                leafBlock::windMover);
    }


}
