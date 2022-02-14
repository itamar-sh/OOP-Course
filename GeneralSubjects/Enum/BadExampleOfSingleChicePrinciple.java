package GeneralSubjects.Enum;

public class BadExampleOfSingleChicePrinciple {

}
enum Operation {
    PLUS, MINUS;
    double eval(double x, double y) {
        switch (this) {
            case PLUS: return x + y;
            case MINUS: return x - y;
        }
        throw new UnsupportedOperationException();
    }
}  // the PLUS MINUS is saved twice - once in the enum declaration and the other in the switch block

// instead we use
enum Operation2 {
    PLUS{
        @Override
        double eval(double x, double y) {
            return x+y;
        }
    }
    , MINUS{
        @Override
        double eval(double x, double y) {
            return x-y;
        }
    };
    abstract double eval(double x, double y);
}

