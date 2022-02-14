package GeneralSubjects.AnonimusClassses;

import java.util.concurrent.Callable;

public class CanAndCantDo {
    private int a;
    private static int b;
    Callable<String> c = new Callable<String>() {
        @Override
        public String call() throws Exception {
            a++;
            b++;b++;
            System.out.println(a);
            System.out.println(b);
            return String.valueOf(a+b);
        }
    };
    Comparable<String> d = new Comparable<String>() {
        @Override
        public int compareTo(String o){
            try {
                c.call();
            }catch (Exception e){

            }
            return 0;
        }
    };
}
class OtherClass{
    public static void main(String[] args) {
        CanAndCantDo e = new CanAndCantDo();
        e.d.compareTo("A");
    }
}