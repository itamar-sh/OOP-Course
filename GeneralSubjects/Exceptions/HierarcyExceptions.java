package GeneralSubjects.Exceptions;

public class HierarcyExceptions {
    public void foo(){
        try{
            throw new A();
        }catch (A a){
        }catch (C c){
        }catch (B b){
        }
        throw new B();
    }
}
class B extends RuntimeException{
}
class A extends C{
}
class C extends Exception{
}