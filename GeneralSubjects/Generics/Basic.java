package GeneralSubjects.Generics;

import java.util.Iterator;
import java.util.List;

public class Basic {
    // filters the rotten - two posiibilities
    private List<? extends Fruit> filterRottenFruits(List<? extends Fruit> fruits){
        Iterator<? extends Fruit> iter = fruits.iterator();
        while(iter.hasNext()){
            Fruit fruit = iter.next();
            if(fruit.isRotten()){
                iter.remove();
            }
        }
        return fruits;
    }
    private <T extends Fruit> List<T> filterRottenFruits2(List<T> fruits){
        Iterator<T> iter = fruits.iterator();
        while(iter.hasNext()){
            T fruit = iter.next();
            if(fruit.isRotten()){
                iter.remove();
            }
        }
        return fruits;
    }

}
class Fruit{
    public boolean isRotten(){
        return true;
    }
}
