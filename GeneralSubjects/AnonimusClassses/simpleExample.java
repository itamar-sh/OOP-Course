package GeneralSubjects.AnonimusClassses;

public class simpleExample {
    SimpleInterface instance = new SimpleInterface() {
        @Override
        public void doSomething() {
            System.out.println("a");
        }
    };
    SimpleClass instance2 = new SimpleClass(){
        @Override
        public void doSomething() {
            System.out.println("c not b");
        }
    };

    public static void main(String[] args) {
        simpleExample s = new simpleExample();
        s.instance.doSomething();
        s.instance2.doSomething();
    }
}
interface SimpleInterface{
    void doSomething();
}

class SimpleClass{
    public void doSomething(){
        System.out.println("b");
    }
}