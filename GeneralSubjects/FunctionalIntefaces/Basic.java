package GeneralSubjects.FunctionalIntefaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

// simple functional interface
@FunctionalInterface
interface Simple{
    void nothing();
    // void nothing2(); if we emit - its not FunctionalInterface anymore and error will occurred.
}
@FunctionalInterface
interface SimpleButWithEverythingElse{
    void nothing();

    // int doesntHaveFields; cant have fields
    public final static int CONSTANTS = 0;
    public static int CONSTANTS2 = 0;
    public final int CONSTANTS3 = 0;
    public int CONSTANTS4 = 0;
    int CONSTANTS5 = 0;  // public, final, static are all redundant
    // private final static int CONSTANTS = 0;  only public constants
    default boolean defautFunc(int num){
        return num>CONSTANTS;
    }
    private boolean privateFunc(int num){  // only methods with body can be private in interface
        return num>CONSTANTS2;
    }
    // private boolean privateFunc2(int num);  must have a body
}
public class Basic {
    public static void main(String[] args) {
        // simple use
        Basic.simpleUse(() -> {}); // gets nothing and return nothing


    }

    public static void simpleUse(Simple simple){
        simple.nothing();
    }
}

class Q6{
    @FunctionalInterface
    interface mathOp{
        float operate(float x, float y);
    }

    float makeMathOp(float x, float y){
        mathOp op = (a,b)->{return b!=0 ? a/b : null;};
        return op.operate(x,y);
    }
}
