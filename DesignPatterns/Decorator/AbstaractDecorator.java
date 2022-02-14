package DesignPatterns.Decorator;
public class AbstaractDecorator {

}




interface Pizza{
    void yummy();
}

abstract class Addition implements Pizza{
    Pizza pizza;
    Addition(Pizza p){
        pizza = p;
    }
}

class Olives extends Addition{

    Olives(Pizza p) {
        super(p);
    }

    @Override
    public void yummy() {
        pizza.yummy();
        System.out.println("but pull them out first");
    }
}