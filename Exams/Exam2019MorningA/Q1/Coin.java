package Exams.Exam2019MorningA.Q1;

public abstract class Coin{
    private final String name;
    protected double value;
    public Coin(String name){
        this.name = name;
        this.value = 0.0;
    }
    public Coin(String name, double value){
        this(name);
        this.value = value;
    }
    public abstract void inflation();
    public String getName(){
        return this.name;
    }
    public void setUSDValue(double usdValue){
        this.value = usdValue;
    }
    public Double getUSDValue(){
        return value;
    }
}
