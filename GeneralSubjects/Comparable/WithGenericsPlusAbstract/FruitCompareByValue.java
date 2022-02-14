package GeneralSubjects.Comparable.WithGenericsPlusAbstract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FruitCompareByValue {
}
class GrapeFruit extends Fruit{}
class Fruit {
    public int getCalories(){
        return 0;
    }
    public static void main(String[] args) {
        List<GrapeFruit> grapefruits = new ArrayList<>();
        // init grapefruites
        // ...
        GrapeFruit grape = getBestFruit(grapefruits); // no cast
    }
    public static <T extends Fruit> T getBestFruit(List<T> lst){
        class FruitWrapper<T>{
            T fruit;
            int caloriesValue;
            public FruitWrapper(T f, int calrories){
                fruit = f;
                caloriesValue = calrories;
            }
        }
        List<FruitWrapper> wrappedLst = new ArrayList<>();
        for(T t: lst){
            wrappedLst.add(new FruitWrapper<T>(t, t.getCalories()));
        }
        FruitWrapper<T> max = Collections.max(wrappedLst, (a, b)->(a.caloriesValue-b.caloriesValue));
        return max.fruit;
    }
}
class Fruit2{
    private static int x=0;
    private int cal=x++;
    public int getCalories(){return cal;}
    public static <S extends Fruit> S getBestFruit(List<S> fruits){
        Collections.sort(fruits,(s1, s2)->Integer.compare(s1.getCalories(),s2.getCalories()));
        return fruits.get(0);
    }
}