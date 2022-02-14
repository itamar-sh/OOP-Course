package GeneralSubjects.Generics;

public class GenericOverride {
}
class SomeClass<T> {
    public <T> T someMethod(T val){
        return val;
    }
}
