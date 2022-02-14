//package GeneralSubjects.FunctionalIntefaces;
//
//public class FuncsManipulation {
//    // a
//    Function<Function<Double>> myFunc = (a)->((b)->(b));
//    // d
//    public static Function<Integer> funcS(String s, Function<Integer> f){
//        switch(s){
//            case "POWER": return (a)-> (f.apply(a)*f.apply(a));
//            case "NEGATIVE": return (a)-> (-f.apply(a));
//            case "FIBONACCI": return (a)-> (f.apply(a-1)+f.apply(a-2)));
//        }
//    }
//}
//@FunctionalInterface
//interface Function<T> {
//    public T apply(T param);
//
//}


/*
Part 1: Enter the correct type for myFunc on the left and insert the right lambda function
on the right side of the initializer
*/
/*insert type*/ //myFunc = /*insert lambda*/

/*
Part 2:
*/

//class Adder implements FuncManipulator{
//// write your implementation
//    public static Function<Double> composeFuncs(Function<Double> f1, Function<Double> f2){
//        return (a)->(f1.apply(a)+f2.apply(a));
//    }
//}
//
//class Composer implements FuncManipulator<Function<Double>>{
//    //write your implementation
//    public static Function<Double> composeFuncs(Function<Double> f1, Function<Double> f2){
//        return (a)->(f1.apply(f2.apply(a)));
//    }
//}

//class FuncManipulatorDecorator<Function<T>> implements FuncManipulator{
//    private funcManipulator<Function<T>> m1 = new Adder<Function<Double>>();
//    private funcManipulator<Function<T>> m2 = new Composer<Function<Double>>();
//
//@Override
//public static Function<T> composeFuncs(Function<T> f1, Function<T> f2){
//        return (a)->(m2.composeFuncs(f,g).apply(m1.composeFuncs(f1,f2).apply(a)));
//        }
//        }
//interface FuncManipulator<Function<T>>{
//    Function<T> composeFuncs(Function<T> a, Function<T> b);
//}
/*
Part 3: Explain the single choice principle here.
*/

/*
Part 4: write your static method here
*/
