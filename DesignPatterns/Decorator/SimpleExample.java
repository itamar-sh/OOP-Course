package DesignPatterns.Decorator;

public class SimpleExample {
    public void decoratorExample(String param){
        Decorator d = new Decorated(new Decorator());
        d.decoratedFunc();
    }
}
class Decorator{
    public Decorator(){

    }
    public void decoratedFunc(){
        // do some stuff
    }
}
class Decorated extends Decorator{
    Decorator d;
    public Decorated(Decorator d){
        this.d = d;
    }
    String param;
    @Override
    public void decoratedFunc(){
        // decoration
        d.decoratedFunc();
        // maybe more decoration
    }
}

