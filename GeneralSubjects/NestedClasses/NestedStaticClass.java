package GeneralSubjects.NestedClasses;

public class NestedStaticClass {
    class Nested{
    }
    static class StaticNested{
    }
    public void examples(){
        NestedStaticClass qs = new NestedStaticClass();
        Nested nested = qs.new Nested();
        StaticNested staticNested = new StaticNested();
    }
}

class Outer{
    private static class SInner{
        private int a;
    }
    public void func(){
        NestedStaticClass.StaticNested staticNested = new NestedStaticClass.StaticNested();
        NestedStaticClass qs = new NestedStaticClass();
        NestedStaticClass.Nested nested = qs.new Nested();
        SInner o = new SInner();
        o.a = 6;
    }
}
