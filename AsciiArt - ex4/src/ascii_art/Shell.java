package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.*;

/**
 * class that control the communication with the user using the shell. (terminal)
 */
public class Shell {
    // init constants
    private static final char[] START_CHARS = {'0','1','2','3','4','5','6','7','8','9'};
    private static final int INITIAL_CHARS_IN_ROW = 64;
    private static final String OUTPUT_FILENAME = "out.html";
    private static final String FONT_NAME = "Courier New";
    private static final int MIN_PIXELS_PER_CHAR = 1;
    // options for operators constants
    private static final String CMD_EXIT = "exit";
    private static final String PREFIX_MESSAGE = ">>> ";
    private static final String ADD_OPERATOR = "add";
    private static final String ADD_PREFIX = "add ";
    private static final String RES_OPERATOR = "res";
    private static final String RES_UP_OPERATOR = "up";
    private static final String RES_DOWN_OPERATOR = "down";
    private static final String REMOVE_OPERATOR = "remove";
    private static final String REMOVE_PREFIX = "remove ";
    private static final String CONSOLE_OPERATOR = "console";
    private static final String RENDERER_OPERATOR = "renderer";
    private static final String CHARS_OPERATOR = "chars";
    private static final String HTML_RENDERING = "html";
    private static final String ALL_OPERATOR = "all";
    private static final String SPACE_OPERATOR = "space";
    private static final int CHARS_CHANGE_OPERATOR_LENGTH = 3;
    private static final int CHANGE_RES_FACTOR = 2;
    private static final char HYPHEN_CHAR = '-';
    private static final int SINGLE_CHAR = 1;
    private static final int MIN_CHAR = 32;
    private static final int MAX_CHAR = 126;
    private static final int START_INDEX = 0;
    private static final int FINISH_INDEX = 1;
    // output method
    private static final ConsoleAsciiOutput consoleAsciiOutput = new ConsoleAsciiOutput();
    private static final String CHANGE_RES_MESSAGE = "Width set to ";
    private static final String MAX_RES_MESSAGE = "the resolution is at max already";
    private static final String MIN_RES_MESSAGE = "the resolution is at min already";
    private static final int RES_MIN_OPERATOR_LENGTH = 5;
    // fields
    private final Set<Character> charSet = new HashSet<>();
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private final BrightnessImgCharMatcher charMatcher;
    private String outputKind = "html";
    private final AsciiOutput output;
    private static final String ERROR_MESSAGE = "input is not valid";
    private static final int SPACE_CHAR_IN_ASCII = 32;
    private static final int HYPHEN_LOCATION = 1;

    /**
     * init an instance: init the charSet with 0 until 9 chars and the sizes of min and max chars in row
     * and the outputs formats.
     * @param img image to work on
     */
    public Shell(Image img) {
        for (char c : START_CHARS){
            this.charSet.add(c);
        }
        minCharsInRow = Math.max(1, img.getWidth()/img.getHeight());
        maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        charMatcher = new BrightnessImgCharMatcher(img, FONT_NAME);
        output = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
    }

    /**
     * The main method of the class - control the flow of the command line
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String user_input = "";
        while(!user_input.equals(CMD_EXIT)){
            controlOperations(user_input);
            System.out.print(PREFIX_MESSAGE);
            user_input = scanner.nextLine();
        }
    }

    /**
     * All the operations asked in the command line are handle here.
     * @param user_input the command to deliver
     */
    private void controlOperations(String user_input) {
        if (user_input.equals("")){
            return;
        }
        if (user_input.contains(ADD_OPERATOR)){
            handleAdd(user_input);
        } else if (user_input.contains(REMOVE_OPERATOR)){
            handleRemove(user_input);
        } else if (user_input.contains(RES_OPERATOR)){
            handleRes(user_input);
        } else if (user_input.contains(CONSOLE_OPERATOR)){
            handleConsole();
        } else if (user_input.contains(RENDERER_OPERATOR)) {
            handleRenderer();
        } else if (user_input.contains(CHARS_OPERATOR)){
            handleChars();
        } else {
            System.out.println(ERROR_MESSAGE);
        }
    }

    /**
     * handle the command 'chars' - show all the chars to display
     */
    private void handleChars() {
        for (Character c: charSet){
            System.out.print(c+ " ");
        }
        System.out.println();
    }

    /**
     * handle the command "renderer". We transform the chars array to Characters array.
     * Find the image by chars and output it.
     */
    private void handleRenderer() {
        // transform the chars array to Characters array
        Character[] charsList = new Character[charSet.size()];
        int i = 0;
        for (char c: charSet) {
            charsList[i] = c;
            i++;
        }
        // find chars that represents image
        char[][] chars = charMatcher.chooseChars(this.charsInRow, charsList);
        // output via the output kind we already know
        if (outputKind.equals(CONSOLE_OPERATOR)){
            consoleAsciiOutput.output(chars);
        } else if (outputKind.equals(HTML_RENDERING)){
            output.output(chars);
        }
    }

    /**
     * Change the output format to console.
     */
    private void handleConsole() {
        this.outputKind = CONSOLE_OPERATOR;
    }

    /**
     * Change the resolution of the image to render
     * @param user_input the command to deliver
     */
    private void handleRes(String user_input) {
        if (user_input.length() < RES_MIN_OPERATOR_LENGTH){
            System.out.println(ERROR_MESSAGE);
        }
        String restArgs = user_input.substring(RES_MIN_OPERATOR_LENGTH-1);
        switch (restArgs){
            case RES_UP_OPERATOR:
                upgradeResolution();
                break;
            case RES_DOWN_OPERATOR:
                downgradeResolution();
                break;
            default:
                System.out.println(ERROR_MESSAGE);
        }
    }

    /**
     * Downgrade the resolution of the image to render
     */
    private void downgradeResolution() {
        if (charsInRow > minCharsInRow){
            charsInRow /= CHANGE_RES_FACTOR;
            System.out.println(CHANGE_RES_MESSAGE + charsInRow);
        } else {
            System.out.println(MIN_RES_MESSAGE);
        }
    }

    /**
     * Upgrade the resolution of the image to render
     */
    private void upgradeResolution() {
        if (charsInRow < maxCharsInRow){
            charsInRow *= CHANGE_RES_FACTOR;
            System.out.println(CHANGE_RES_MESSAGE + charsInRow);
        } else {
            System.out.println(MAX_RES_MESSAGE);
        }
    }

    /**
     * Remove chars from the charSet that determine which chars will may make the rendered image
     * @param user_input the command to deliver
     */
    private void handleRemove(String user_input) {
        if (!user_input.startsWith(REMOVE_PREFIX)){
            System.out.println(ERROR_MESSAGE);
        } else {
            String removeArgs = user_input.substring(REMOVE_PREFIX.length());
            findOperationForAddOrRemove(removeArgs, REMOVE_OPERATOR);
        }
    }

    /**
     * Add chars from the charSet that determine which chars will may make the rendered image
     * @param user_input the command to deliver
     */
    private void handleAdd(String user_input) {
        if (!user_input.startsWith(ADD_PREFIX)){
            System.out.println(ERROR_MESSAGE);
        } else {
            String addArgs = user_input.substring(ADD_PREFIX.length());
            findOperationForAddOrRemove(addArgs, ADD_OPERATOR);
        }
    }

    /**
     * Manipulate the string that decide the operator for remove or add chars.
     * @param Args input of chars to remove or add
     * @param operation add or remove
     */
    private void findOperationForAddOrRemove(String Args, String operation) {
        if (Args.length() == SINGLE_CHAR){
            changeChars(new int[]{(int)Args.charAt(0), (int)Args.charAt(0)}, operation);
            return;
        }
        switch (Args){
            case ALL_OPERATOR:
                changeChars(new int[]{MIN_CHAR, MAX_CHAR}, operation);
                break;
            case SPACE_OPERATOR:
                changeChars(new int[]{SPACE_CHAR_IN_ASCII, SPACE_CHAR_IN_ASCII}, operation);  // ' ' is 32 in ascii
                break;
            default:  // general case: "a-x" style
                if (Args.length() == CHARS_CHANGE_OPERATOR_LENGTH && Args.charAt(HYPHEN_LOCATION) == HYPHEN_CHAR){
                    int[] section = new int[]{Args.charAt(0), Args.charAt(CHARS_CHANGE_OPERATOR_LENGTH-1)};
                    Arrays.sort(section);
                    changeChars(section, operation);
                } else {
                    System.out.println(ERROR_MESSAGE);
                }
        }
    }

    /**
     * Add or remove chars from the charSet that consist the chars of the rendered image
     * @param section two values arrays of int, represents the finish and the start of the section to add
     * @param operation string represents the operator
     */
    private void changeChars(int[] section, String operation) {
        if (operation.equals(ADD_OPERATOR)){
            for (int i = section[START_INDEX]; i <= section[FINISH_INDEX]; i++) {
                charSet.add((char)i);  // must be integer between 1 and 255 so downcast is okay.
            }
        } else if(operation.equals(REMOVE_OPERATOR)){
            for (int i = section[START_INDEX]; i <= section[FINISH_INDEX]; i++) {
                charSet.remove((char)i);  // must be integer between 1 and 255 so downcast is okay.
            }
        }
    }
}
