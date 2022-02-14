package GeneralSubjects.Inharitance;

public class Shadowing {
    public static void main(String[] args) {
        A a1 = new B();
        // B b2 = new A();  wont compile. B pointer cant have A Object
        // in methods the object its what important.
        System.out.println(a1.getField());  // 8
        // in fields the pointer its what important
        System.out.println(a1.a);  // 10
        // After casting the fields and methods are as always
        System.out.println(((B)a1).a);  // 8
        System.out.println(((B)a1).getField());  // 8
        // casting acts as above
        B b = new B();
        System.out.println(((A)b).a);  // 10
        System.out.println(((A)b).getField());  // 8
    }
}

class A{
    A(){}
    A(int a){}
    public int a = 10;
    public int getField(){
        return a;
    }
}
class B extends A{
    B(){}
    B(String a){}
    B(int a){super(a);}
    public int a = 8; // classic shadowing. The B object usually point to this object.
    public int getField(){  // classic override. The B object usually point to this method.
        return a;
    }
}