package GeneralSubjects.Generics;

public class Super_Extend_Wildcard_example {
    interface myInt {}
    interface ourInt extends myInt {}
    interface ourInt2 extends ourInt {}
    static class sClass<T> {}

    static void foo(sClass<? super myInt> arg) {}


    static void boo(sClass<? extends myInt> arg) {}

    public static void main(String[] args) {
        /* 1 */ foo (new sClass<myInt>()); // compile because every class is extends of herself
 //     /* 2 */ foo (new sClass<ourInt>()); //wont compile because <? super myInt> gets only things myInt are extended from
 //     /* 3 */ foo (new sClass<ourInt2>()); // wont compile for same reason
        /* 4 */ foo (new sClass());  // compile because we raw use (?)

        /* 1 */ boo (new sClass<myInt>()); // compile because every class is super of herself
        /* 2 */ boo (new sClass<ourInt>());
        /* 3 */ boo (new sClass<ourInt2>());
        /* 4 */ boo (new sClass());  // compile because we raw use (?)

    }
}
