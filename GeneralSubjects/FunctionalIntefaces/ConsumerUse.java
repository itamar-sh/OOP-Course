package GeneralSubjects.FunctionalIntefaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerUse {
    public static void main(String[] args) {
        // Consumer use
        List<String> l = Arrays.asList("one", "two", "tree");
        Consumer<String> consumer = s -> System.out.println(s);  // lambda expression - gets one thing and return nothing
        l.forEach(consumer); // gets Consumer
    }
}
