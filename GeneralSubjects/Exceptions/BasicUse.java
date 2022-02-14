package GeneralSubjects.Exceptions;

public class BasicUse {
    public static void main(String[] args) {
        try {
            General1();
        }catch (Exception a){
            System.out.println(a.getMessage());
        }
        try {
            A1();
        }catch (Exception a){
            System.out.println(a.getMessage());
        }
    }
    public static void General1() throws Exception{
        throw new RuntimeException("general1");
    }
    public static void A1() throws ExceptionA{
        ExceptionA a = new ExceptionA("a1");
        throw a;
    }

}
class ExceptionA extends Exception{
    public ExceptionA(String s){
        super(s);
    }
}
