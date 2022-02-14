package Exams.Exam2019B;

public class Q6FromSmall {
    public static Clock buildClock(int initialHour, int totalHours){
        class SimpleClock extends Clock{
            private int Chours;
            private int CtotalHours;
            public SimpleClock(int _initialHour, int _totalHours){
                super(_initialHour);
                Chours = _initialHour;
                CtotalHours = _totalHours;
            }
            public void advance(Integer a){
                Chours = (Chours + a) % CtotalHours;
            }

        }
        return new SimpleClock(initialHour, totalHours);
    }
}

interface Advanceable<T> {
    void advance(T units);
}
abstract class Clock implements Advanceable<Integer> {
    protected int hour;
    Clock(int initialHour) {
        this.hour = initialHour;
    }
    public int getHour() {
        return this.hour;
    }
}