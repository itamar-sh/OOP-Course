package GeneralSubjects.Enum;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

public class EnumReplacment {
    public static void main(String[] args) {
        //ShoeBrand shoeBrand = new ShoeBrand(); // compilation error
        ShoeBrand localBrand = new SomeLocalBrand(); // must work!
        ShoeBrand nike = ShoeBrand.NIKE;
        ShoeBrand adidas = ShoeBrand.ADIDAS;
        //ShoeBrand.NIKE = new SomeLocalBrand(); // compilation error
    }
}
class SomeLocalBrand extends ShoeBrand{}

abstract class ShoeBrand {
    static private List<ShoeBrand> values = new LinkedList<>();

    public ShoeBrand() {
        ShoeBrand.values.add(this);
    }

    public static final ShoeBrand NIKE = new ShoeBrand() {}; // {} for anonymous classes
    public static final ShoeBrand ADIDAS = new ShoeBrand() {}; // {} for anonymous classes

    public static List<ShoeBrand> getValues() {
        return values;
    }
}