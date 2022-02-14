package DesignPatterns.Iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public class IteratorExample2019SummerB {
    public static Collection<Double> findDuplicate(Collection<Double> col){
        Iterator<Double> iter = col.iterator();
        ArrayList<Double> arr = new ArrayList<>();
        Double last = 0.0;
        while(iter.hasNext()){
            Double cur = iter.next();
            if(cur.equals(last)){
                arr.add(cur);
            }
            last = cur;
        }
        return arr;
    }
    // another option
    public static Collection<Double> findDuplicate2(Collection<Double> col){
        return new TreeSet<>(col);
    }
}
