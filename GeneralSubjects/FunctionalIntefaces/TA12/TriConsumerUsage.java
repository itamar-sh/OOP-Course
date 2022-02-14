package GeneralSubjects.FunctionalIntefaces.TA12;

public class TriConsumerUsage {
    public static void main(String[] args) {
        callTriConsumers(
                (a, b, c) -> System.out.println(a + b + c),
                (a, b, c) -> System.out.println(a * b * c),
                1.5, 4, 5.5);
    }

    public static void callTriConsumers(
            TriConsumer<Double, Double, Double> first,
            TriConsumer<Double, Double, Double> second,
            double a, double b, double c) {
        //first.accept(a,b,c);
        //second.accept(a,b,c);
        first.andThen(second).accept(a, b, c);
    }
}