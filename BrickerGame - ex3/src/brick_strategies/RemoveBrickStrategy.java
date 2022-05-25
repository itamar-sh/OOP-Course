package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

// CollisionStrategy that removes the bricks from the game
public class RemoveBrickStrategy implements CollisionStrategy{

    private final GameObjectCollection gameObjectCollection;

    /**
     * simple constructor
     * @param gameObjectCollection collection off all the objects in the current game
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection){
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * when called we will remove the brick from the game.
     * @param thisObj a brick object that has this strategy
     * @param otherObj a ball object that collide with our brick
     * @param counter counter of bricks
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        this.gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        counter.decrement();
    }

    /**
     * @return the collection of the objects of the game.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjectCollection;
    }
}
