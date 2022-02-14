package Exams.Exam2019B;

import java.util.HashMap;
import java.util.Map;

public class Q5FromSmall {
    public static boolean isAnagram(String a, String b) {
        a = a.toUpperCase();
        b = b.toUpperCase();
        if (a.length() != b.length())
            return false;
        int asciiMin = 65;
        int[] hist = new int[26];
        for (int i = 0; i < a.length(); i++) {
            hist[(int) a.charAt(i) - asciiMin]++;
            hist[(int) b.charAt(i) - asciiMin]--;
        }
        for (int i = 0; i < 26; i++) {
            if (hist[i] != 0) {
                return false;
            }
        }
        return true;
    }

}
