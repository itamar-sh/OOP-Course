package Exams.Exam2018A;

import java.util.Comparator;
import java.util.List;

public class Q3 {
}
// a
interface Settable<E extends Settable<E>>{
    void set(E a);
}
//b
class Pair<V1 extends Comparable<V1>, V2 extends Comparable<V2>> implements Comparable<Pair<V1,V2>>, Settable<Pair<V1,V2>>{
    private V1 v1;
    private V2 v2;
    public Pair(V1 a, V2 b){
        v1 = a;
        v2 = b;
    }

    public int compareTo(Pair<V1, V2> o) {
        return 0;
    }

    public void set(Pair<V1,V2> a){
        this.v1 = ((Pair<V1,V2>)a).v1;
        this.v2 = ((Pair<V1,V2>)a).v2;
    }

    @Override
    public String toString() {
        return v1.toString() + v2.toString();
    }
    // c
    public static <T extends Comparable<T>, E extends Comparable<E>> Comparator<Pair<T, E>> getV2V1Comparator(T t, E e){
        return (pair1, pair2) -> {
            if(pair1.v2 == pair2.v2){
                return pair1.v1.compareTo(pair2.v1);
            }
            else{
                return  ((Pair<T,E>)pair1).v2.compareTo(((Pair<T,E>)pair1).v2);
            }
        };
    }
    // d
    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> void printList(List<Pair<T1, T2>> lst){
        for (Pair<T1, T2> p: lst){
            System.out.println(p);
        }
    }
    // e
}
