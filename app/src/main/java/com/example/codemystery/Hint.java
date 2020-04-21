package com.example.codemystery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Hint {
    int minR = 0;
    int maxR = 999;
    String actualNumber;

    public Hint(String actual){
        this.actualNumber = actual;
    }
    private static int getRandomNumberInRange(int min, int max) { // https://mkyong.com/java/java-generate-random-integers-in-a-range/
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private String getRandomNumberStringDefaultRange(){
        return Integer.toString(getRandomNumberInRange(this.minR, this.maxR));
    }

    public static String shuffleString(String string) // https://stackoverflow.com/a/11130209
    {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) {
            shuffled += letter;
        }
        return shuffled;
    }

    public String oneCorrectWellPlaced(){
        String resultString = "";
        boolean condition =
                (resultString.charAt(0) == actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2)) ||
                (resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) == actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2)) ||
                (resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) == actualNumber.charAt(2));
        while(!condition){
            resultString = getRandomNumberStringDefaultRange();
        }
        return resultString;
    }
    public String oneCorrectWrongPlaced(){
        String resultString = "";
        boolean condition = resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2);
        while(!condition){
            resultString = shuffleString(oneCorrectWellPlaced());
        }
        return resultString;
    }
    public String twoCorrectWrongPlaced(){
        String resultString = "";
        boolean condition = ((actualNumber.contains(String.valueOf(resultString.charAt(0)) + String.valueOf(resultString.charAt(1)))) ||
                            (actualNumber.contains(String.valueOf(resultString.charAt(1)) + String.valueOf(resultString.charAt(2)))) ||
                            (actualNumber.contains(String.valueOf(resultString.charAt(0)) + String.valueOf(resultString.charAt(2))))) &&
                            resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2);
        while(!condition){
            resultString = shuffleString(oneCorrectWellPlaced());
        }
        return resultString;
    }
    public String noneCorrect(){
        String resultString = "";
        boolean condition = resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2);
        while(!condition){
            resultString = getRandomNumberStringDefaultRange();
        }
        return resultString;
    }
}
