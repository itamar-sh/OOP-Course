package GeneralSubjects.FunctionalIntefaces;

public class Example {
    public static void doesSomething(Example2 example){
        example.doSomething(2);
    }

    public static void main(String[] args) {
        doesSomething((int i) -> i*2);
    }
}

interface Example2{
    int doSomething(int i);
}

