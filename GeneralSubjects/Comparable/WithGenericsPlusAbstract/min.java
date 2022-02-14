package GeneralSubjects.Comparable.WithGenericsPlusAbstract;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

public class min {
}
class Computer{
    public static Double minA(ArrayList<Computable> arr){
        return Collections.min(arr, (a,b)->((int)(a.compute() - b.compute()))).compute();
    }
    public static Double minB(ArrayList<Computable> arr){
        ArrayList<Double> arr2 = new ArrayList<>();
        for(Computable c: arr){
            arr2.add(c.compute());
        }
        return Collections.min(arr2, (a,b)->((int)(a-b)));
    }
    public static Double minC(ArrayList<Computable> arr){
        ArrayList<Double> arr2 = new ArrayList<>();
        for(Computable c: arr){
            arr2.add(c.compute());
        }
        return Collections.min(arr2);
    }
}
interface Computable{
    Double compute();
}