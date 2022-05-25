package ascii_art;

import java.util.HashMap;

public class Algorithms {
    private static final int NUM_WORDS_IN_ENGLISH = 26;
    private static final int A_LOCATION_IN_ASCII_MAP = 97;

    /**
     * We find the duplicate in an array of ints.
     * The technique is based on running with two variables. each one is jumping to the location of the current
     * value in the int. so if numList[0]==5 the next jump will be to the numList[5].
     * One variable jump one jump every iteration (slow). The second jump two jumps every iteration. (fast).
     * In the end we find a loop and then the value that is written twice in the loop.
     * @param numList array of int from 1 to n with one duplicate value.
     * @return the value of the duplicate number
     */
    public static int findDuplicate(int[] numList) {
        // Init the variables based on getting one iteration already.
        int slow = numList[numList[0]];
        int fast = numList[numList[numList[0]]];
        // find a cycle
        while (slow != fast){
            slow = numList[slow];
            fast = numList[numList[fast]];
        }
        // Find the duplicate value that close the cycle.
        slow = numList[0]; // we want to go from the start with the slow while the fast is stuck in the loop.
        while (slow != fast) {
            slow = numList[slow];
            fast = numList[fast];
        }
        return fast;
    }

    /**
     * By making dictionary that hold the relation between the chars and their meaning in Morse Code And by
     * holding another dictionary that hold the relation between the results and the words, we simply cannot
     * get more results of words in the dictionary than the actual number of them so every two same results combined to
     * one result. (we could use a set, and it will do the same trick, but it's the same run time.)
     * @param words list of words in english
     * @return num of words in english from the input with the same Morse Code value.
     */
    public static int uniqueMorseRepresentations(String[] words){
        // init chars to morse code map
        HashMap<Character, String> charToMorse= new HashMap<>();
        String[] morseCode
                = { ".-",   "-...", "-.-.", "-..",  ".",
                "..-.", "--.",  "....", "..",   ".---",
                "-.-",  ".-..", "--",   "-.",   "---",
                ".--.", "--.-", ".-.",  "...",  "-",
                "..-",  "...-", ".--",  "-..-", "-.--",
                "--..", "|" };
        // for every char we put the value in the dictionary that know relation of chars and their meaning in Morse Code
        for (int i = 0; i < NUM_WORDS_IN_ENGLISH; i++) {
            char c = (char)(i+A_LOCATION_IN_ASCII_MAP);
            charToMorse.put(c, morseCode[i]);
        }
        // calculate every word and enter the morse code to that word a map from morse code to words
        HashMap<String, String> morseToWords = new HashMap<>();
        for (String word: words){
            String[] curMorse = new String[word.length()];  // new word in Morse Code
            for (int i = 0; i < word.length(); i++) {
                curMorse[i] = charToMorse.get(word.charAt(i));
            }
            // enter the new word to the results dictionary. If the word is already places than it will overwrite.
            String completeWordInMorse = String.join("", curMorse);  // unite the strings to one word
            morseToWords.put(completeWordInMorse, word);
        }
        return morseToWords.size(); // the size of the dictionary is the num of the results.
    }



}
