package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * a class deals with a strategy of brick that has two different strategies. can't be classic strategy of remove brick
 * and can be, in 1/30 chance has three strategies.
 */
public class DoubleBehaviourStrategy implements CollisionStrategy {
    private CollisionStrategy firstStrategy;
    private CollisionStrategy secondStrategy;
    private CollisionStrategy thirdStrategy;
    private GameObjectCollection gameObjectCollection;

    /**
     * constructor of case of three strategies (1/30 chance)
     * @param firstStrategy some strategy. Adding paddle or change camera or change speed and more
     * @param secondStrategy some strategy. Adding paddle or change camera or change speed and more
     * @param thirdStrategy some strategy. Adding paddle or change camera or change speed and more
     */
    public DoubleBehaviourStrategy(CollisionStrategy firstStrategy,
                                   CollisionStrategy secondStrategy, CollisionStrategy thirdStrategy) {
        this.firstStrategy = firstStrategy;
        this.secondStrategy = secondStrategy;
        this.thirdStrategy = thirdStrategy;
        this.gameObjectCollection = firstStrategy.getGameObjectCollection();
    }

    /**
     * constructor of case of two strategies case.
     * @param firstStrategy some strategy. Adding paddle or change camera or change speed and more
     * @param secondStrategy some strategy. Adding paddle or change camera or change speed and more
     */
    public DoubleBehaviourStrategy(CollisionStrategy firstStrategy,
                                   CollisionStrategy secondStrategy) {
        this.firstStrategy = firstStrategy;
        this.secondStrategy = secondStrategy;
        this.thirdStrategy = null;
        this.gameObjectCollection = firstStrategy.getGameObjectCollection();
    }

    /**
     * when called we will active the other two strategies.
     * @param thisObj a brick object that has this strategy
     * @param otherObj a ball object that collide with our brick
     * @param counter counter of bricks
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        this.firstStrategy.onCollision(thisObj, otherObj, counter);
        this.secondStrategy.onCollision(thisObj, otherObj, counter);
        if (this.thirdStrategy != null){
            this.thirdStrategy.onCollision(thisObj, otherObj, counter);
        }
    }

    /**
     * @return simply return the objects collection
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjectCollection;
    }
}
