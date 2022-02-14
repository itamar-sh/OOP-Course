package Exams.Exam2019MorningA.Q1;

public class CryptoCurrency extends Coin {
    public CryptoCurrency(String name) {
        super(name);
    }
    public CryptoCurrency(String name, double value) {
        super(name, value);
    }

    public void inflation() {
        this.value = this.value*0.9;
    }
}