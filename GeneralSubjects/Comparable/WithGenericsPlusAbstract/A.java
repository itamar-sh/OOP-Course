package GeneralSubjects.Comparable.WithGenericsPlusAbstract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// first we don't need to implement CompareTo just yet. because A is abstract.
// second we don't necessary have to make abstract method for abstract class
public abstract class A<E> implements Comparable<A<E>>{
    public String toString(){
        return this.getClass().getName();
    }
}
// now we have to implement compareTo
class B<E> extends A<E> {
    public int compareTo(A<E> a){
        if(a instanceof B) return 0;
        return -a.compareTo(this);
    }
}
class C<E> extends A<E> {
    public int compareTo(A<E> a) { return - new D<E>().compareTo(a); }
}
class D<E> extends A<E> {
    public int compareTo(A<E> a) { return 1; }
}
class MainABCD{
    public static void main(String[] args) {
        List<A<Integer>> list = new ArrayList<>();
        list.add(new C<>());
        list.add(new C<>());
        list.add(new D<>());
        list.add(new B<>());
        list.add(new B<>());
        Collections.sort(list); // every Obj do compare to with the next obj as parameter.
        for(A<?> item : list)
            System.out.println(item);
    }
}
