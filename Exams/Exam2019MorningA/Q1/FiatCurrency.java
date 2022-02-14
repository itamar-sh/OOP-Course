package Exams.Exam2019MorningA.Q1;

public class FiatCurrency extends Coin{
    public FiatCurrency(String name) {
        super(name);
    }
    public FiatCurrency(String name, double value) {
        super(name, value);
    }

    @Override
    public void inflation() {
        this.value =  this.value*0.9;
    }
}
