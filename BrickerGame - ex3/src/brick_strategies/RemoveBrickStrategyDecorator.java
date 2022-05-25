package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * class that take a strategy an add to her the strategy of removeBrick. can't have instances of her own(abstract).
 */
public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {

    private final CollisionStrategy toBeDecorated;
    private final RemoveBrickStrategy removeBrickStrategy;

    /**
     * constructor
     * @param toBeDecorated a strategy that we have and want to add her the brick strategy
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated){
        this.toBeDecorated = toBeDecorated;
        removeBrickStrategy = new RemoveBrickStrategy(this.toBeDecorated.getGameObjectCollection());
    }

    /**
     * remove the brick. this method has benn called brfore the real strategy happens (via super word)
     * @param thisObj a brick object that has this strategy
     * @param otherObj a ball object that collide with our brick
     * @param counter counter of bricks
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        removeBrickStrategy.onCollision(thisObj, otherObj, counter);
    }

    /**
     * @return the collection of the objects of the game.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }
}
