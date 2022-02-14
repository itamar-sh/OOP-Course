package GeneralSubjects.Generics;

import java.util.LinkedList;

public class Covariance {
}
/*
Generic are Invariant
    • Invariance: for any class / interface MyClass and two distinct
    types Type1 and Type2:
    • MyClass<Type1> is neither a subtype nor a supertype of
    MyClass<Type2>
 */

class Q5P2{
    //LinkedList<Object> = new LinkedList<String>(); //doesn't compile due to invariance
}

    /*
    Q5 Part 3:
    We can use wildcards instead
     */

class Q5P3{

    public static void main(String[] args) {
        LinkedList<? extends Object> arr = new LinkedList<String>();
        arr = new LinkedList<String>();
    }
}
class Person{
    String id;
    public Person(String id){
        this.id = id;
    }
    @Override
    public boolean equals(Object o){
        if(! (o instanceof Person)){
            return false;
        }
        return this.id.equals(((Person)o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

