package GeneralSubjects.Regex;

public class ComplexRegex {
    public static void main(String[] args) {


    }
    // all the chars are identical. 2018A Q6
    public static boolean mysteryFunction(int n) {
        String str = new String(new char[n]);
        return !str.matches(".?|(..+?)\\1+");
    }
}
