package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * class that control the chars arrays represents the image.
 * The chars are selected by their brightness.
 */
public class BrightnessImgCharMatcher {
    private static final double RED_BRIGHTNESS = 0.2126;
    private static final double GREEN_BRIGHTNESS = 0.7152;
    private static final double BLUE_BRIGHTNESS = 0.0722;
    private static final int MAX_CHARS = 255;
    private final Image image;
    private final String fontName;
    private static final int PIXELS = 16;
    private final HashMap<Image, Double> cache = new HashMap<>();
    private int lastPixels = 0;

    /**
     * Init instance of class
     * @param image image to find the average brightness from
     * @param fontName the font we check with the brightnesses.
     */
    public BrightnessImgCharMatcher(Image image, String fontName) {
        this.image = image;
        this.fontName = fontName;
    }

    /**
     *
     * @param numCharsInRow int represents how many chars we will have in each row at the final result
     * @param charSet arrays of chars that can be in the final result
     * @return 2D char arrays represents the image in Ascii Art.
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet){
        return convertImageToAscii(this.image, numCharsInRow, charSet, this.fontName);
    }

    /**
     * Main method of this class. Gets an Image and find their 2D char arrays represents the image in Ascii Art.
     * @param image image to find her Ascii picture
     * @param numCharsInRow int represents how many chars we will have in each row at the charsBrightnesses result
     * @param chars arrays of chars that can be in the charsBrightnesses result
     * @param fontName the font we will write the chars in the charsBrightnesses image of chars
     * @return 2D char arrays represents the image in Ascii Art.
     */
    private char[][] convertImageToAscii(Image image, int numCharsInRow, Character[] chars, String fontName){
        // init values
        int pixels = image.getWidth()/numCharsInRow;
        int rows = image.getHeight()/pixels;
        int cols = image.getWidth()/pixels;
        double[][] charsBrightnesses = new double[rows][cols];
        // find brightnesses of all the sub images and chars
        insertBrightnessForImage(image, pixels, charsBrightnesses);
        double[] charsBrightness = charsToBrightness(chars, fontName);
        Map<Double, Character> mapBrightnessToChar = makeMap(chars, charsBrightness); //map every char to his brightness
        //  we pick for every value in charsBrightnesses a char
        return mapCharToBrightness(rows, cols, charsBrightnesses, mapBrightnessToChar, charsBrightness);
    }

    /**
     * Insert to 2D double arrays the brightnesses of chars that represents an image.
     * @param image image that will be represented by chars
     * @param pixels pixels of the final image.
     * @param brightnesses the values of the final image
     */
    private void insertBrightnessForImage(Image image, int pixels, double[][] brightnesses) {
        int cols = brightnesses[0].length;
        int i = 0;
        // if the pixels is changed so we need to re calculate all the subImages brightnesses
        if (pixels == this.lastPixels){
            for(Image subImage : image.squareSubImagesOfSize(pixels)) {
                brightnesses[i/cols][i%cols] = this.cache.get(subImage);
                i++;
            }
        } else {
            for(Image subImage : image.squareSubImagesOfSize(pixels)) {
                brightnesses[i/cols][i%cols] = averageBrightnessPixel(subImage);
                this.cache.put(subImage, brightnesses[i/cols][i%cols]);
                i++;
            }
            this.lastPixels = pixels;
        }
    }

    /**
     * Takes array of chars and return array of double that represents the brightness of each char.
     * There is importance to the order. so chars[i] is a char with brightness charsBrightness[i].(double)
     * @param chars array of chars to find their brightness
     * @param fontName the font we check with the brightnesses.
     * @return array of doubles represents the brightness of each char.
     */
    private static double[] charsToBrightness(Character[] chars, String fontName){
        double[] charsBrightness = new double[chars.length];
        for (int i = 0; i < chars.length; i++) {
            boolean[][] curCharImg = CharRenderer.getImg(chars[i], PIXELS, fontName);
            charsBrightness[i] = calculateTrueSum(curCharImg)/(PIXELS*PIXELS);
        }
        return linearStretching(charsBrightness);
    }

    /**
     * make linear stretching for array of doubles. First find the min and the max values. Then update the values
     * via this formula: newValue= (OldValue-min)/(max-min).
     * @param charsBrightness array to perform linear stretching on.
     * @return new array that has the values of the given array after performing the linear stretching.
     */
    private static double[] linearStretching(double[] charsBrightness){
        // find max and min brightness
        double maxBrightness = findMax(charsBrightness);
        double minBrightness = findMin(charsBrightness);
        // check that the max and the min different
        if (maxBrightness == minBrightness){
            return charsBrightness;
        }
        // change every pixel with via the formula: = (curBrightness-minBrightness)/(maxBrightness-minBrightness)
        double[] newCharsBrightness = new double[charsBrightness.length];
        double different = maxBrightness - minBrightness;
        for (int i = 0; i < charsBrightness.length; i++){
            newCharsBrightness[i] = (charsBrightness[i]-minBrightness)/different;
        }
        return newCharsBrightness;
    }

    /**
     * we pick for every value in charsBrightnesses a char by brightness
     * @param rows rows of the new made image
     * @param cols cols of the new made image
     * @param charsBrightnesses values of the brightness of the new made image
     * @param mapBrightnessToChar map from every brightness (double between 0 and 1) to char
     * @param notOrderedCharsBrightness all the brightnesses of our chars, not sorted.
     * @return 2D array of chars represents new made image made by chars.
     */
    private char[][] mapCharToBrightness(int rows, int cols, double[][] charsBrightnesses,
                                         Map<Double,Character> mapBrightnessToChar, double[] notOrderedCharsBrightness) {
        double[] orderedCharsBrightness = getOrderedArray(notOrderedCharsBrightness);
        char[][] imageChars = new char[rows][cols];
        for (int j = 0; j < rows; j++) {
            for (int k = 0; k < cols; k++) {
                double closetValue = findClosestValue(orderedCharsBrightness, charsBrightnesses[j][k]);
                imageChars[j][k] = mapBrightnessToChar.get(closetValue);
            }
        }
        return imageChars;
    }

    /**
     * gets 2 arrays, one of chars and second of double and make a map that connects between them.
     * the order is what connects between each pair. chars[i] with charsBrightness[i].
     * @param chars array of chars.
     * @param charsBrightness array of brightnesses.
     * @return map(dictionary) that contains all the pairs in the arrays.
     */
    private Map<Double, Character> makeMap(Character[] chars, double[] charsBrightness) {
        Map<Double, Character> mapCharsToBrightness = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            mapCharsToBrightness.put(charsBrightness[i], chars[i]);
        }
        return mapCharsToBrightness;
    }

    /**
     * given 2D array of boolean, represents image, and calculates the sum of the true values inside.
     * @param curCharImg 2D array of boolean, represents image
     * @return sum of the true values inside.
     */
    private static double calculateTrueSum(boolean[][] curCharImg) {
        double sum = 0;
        for (int i = 0; i < curCharImg.length; i++){
            for (int j = 0; j < curCharImg[i].length; j++){
                if (curCharImg[i][j]){
                    sum++;
                }
            }
        }
        return sum;
    }



    /**
     * find the average brightness of image
     * @param image image to find the average brightness from
     * @return double value represents the average brightness of given image.
     */
    private static double averageBrightnessPixel(Image image){
        int pixelsCounter = 0;
        double brightnessSum = 0;
        for (Color pixel: image.pixels()) {
            pixelsCounter++;
            double red = (double)pixel.getRed() * RED_BRIGHTNESS;
            double green = (double)pixel.getGreen() * GREEN_BRIGHTNESS;
            double blue = (double)pixel.getBlue() * BLUE_BRIGHTNESS;
            brightnessSum += red + green + blue;
        }
        return (brightnessSum/(pixelsCounter))/MAX_CHARS;
    }

    /**
     * gets an array of doubles and a value and find the closest value in the array to this value.
     * @param charsBrightness array of doubles to find the closest value from
     * @param curCharBrightness value to find the closest value to
     * @return the closest value to the curCharBrightness
     */
    private double findClosestValue(double[] charsBrightness, double curCharBrightness) {
        for (int i = 0; i < charsBrightness.length; i++) {
            if (curCharBrightness <= charsBrightness[i]){
                if (i == 0){
                    return charsBrightness[0];
                }
                if (charsBrightness[i]-curCharBrightness < curCharBrightness - charsBrightness[i-1]){
                    return charsBrightness[i];
                } else {
                    return charsBrightness[i-1];
                }
            }
        }
        return 0;  // not supposed to get here
    }

    /**
     * return the min value of arrays of double values between 0 and 1
     * @param charsBrightness arrays of double values between 0 and 1
     * @return min value
     */
    private static double findMin(double[] charsBrightness) {
        double minBrightness = 1;
        for (double brightness : charsBrightness) {
            if (brightness < minBrightness) {
                minBrightness = brightness;
            }
        }
        return minBrightness;
    }

    /**
     * return the max value of arrays of double values between 0 and 1
     * @param charsBrightness arrays of double values between 0 and 1
     * @return max value
     */
    private static double findMax(double[] charsBrightness) {
        double maxBrightness = 0;
        for (double brightness : charsBrightness) {
            if (brightness > maxBrightness) {
                maxBrightness = brightness;
            }
        }
        return maxBrightness;
    }

    /**
     * make new ordered array out of the current array.
     * @param charsBrightness array to sort
     * @return sorted array
     */
    private double[] getOrderedArray(double[] charsBrightness) {
        double[] orderedCharsBrightness = Arrays.copyOf(charsBrightness, charsBrightness.length);
        Arrays.sort(orderedCharsBrightness);
        return orderedCharsBrightness;
    }

}
