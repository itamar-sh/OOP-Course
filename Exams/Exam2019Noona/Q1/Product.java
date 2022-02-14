package Q1;

public class Product implements Valuable{
    private String name;
    private double rawValue;
    public Product(String name, double rawValue){
        this.name = name;
        this.rawValue = rawValue;
    }

    public double rawValue(){
        return rawValue;
    }
}
