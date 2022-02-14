package GeneralSubjects.FunctionalIntefaces;

import com.sun.tools.javac.Main;

public interface InterfaceBasic {
    // not allowed:
    /*

    private void func1();    private/protected func/variable -
    protected void func1();
    public int b;
    private int c = 5;
    private int d;
    protected int e;
    protected int f = 5;
    int g;
    */
    // allowed (its static and public)
    public int a = 5;
    int h = 5;  // public by default - there is no package modifier
    // static variables is ok but have to be init. all the fields are static by default! like constants
    static public int a1 = 5;
    static int a2 = 5;

    // default methods are private and nothing else
    private int func1(){ // no different between private and default in default methods
        return 5;
    }
    default int func2(){
        return 5;
    }
    /*
    won't compile - there is no protected/public/int for default method
    protected int func2(){return 5;}
    public int func3(){return 5;}
    int func4(){return 5;}
     */
    static int Sfunc(){return 5;};  // static method are ok - its default methods but static!


}
interface InterfaceBasic2{
    int a2 = 5;
    static int Sfunc(){return 5;};  // static method are ok - its default methods
    default int func2(){
        return 6;
    }
    public static int a1 = 6;
}
class A implements InterfaceBasic, InterfaceBasic2{
    int a2 = 10;
    public static void main(String[] args) {
        A a = new A();
        System.out.println(InterfaceBasic.a1);
        // a.func2();
        System.out.println(A.a);
        // System.out.println(A.a1);  name clashing - should use shadowing for changing subjects

    }
    // has to Override even though its default because the names are clashing and its not static
    // only trying to use them will make compilation error
    @Override
    public int func2(){
        return 7;
    }
}

class run{
    public static void main(String[] args) {
        InterfaceBasic a = new A();
        System.out.println(a.func2());  // like always - the method is by the object
        System.out.println(a.a2);  // like always - the fields is by the object
        A a1 = new A();
        System.out.println(a1.a2);
    }
}
