package com.example.codemystery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Hint {
    private String actualNumber;

    public Hint(String actual){
        this.actualNumber = actual;
    }

    public static String shuffleString(String string) // https://stackoverflow.com/a/11130209
    {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        StringBuilder shuffled = new StringBuilder();
        for (String letter : letters) {
            shuffled.append(letter);
        }
        return shuffled.toString();
    }

    public String oneCorrectWellPlaced(){
        String resultString = "";
        boolean condition =
                (resultString.charAt(0) == actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2)) ||
                (resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) == actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2)) ||
                (resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) == actualNumber.charAt(2));
        while(!condition){
            resultString = MainActivity.getRandomNumberStringDefaultRange();
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
            resultString = MainActivity.getRandomNumberStringDefaultRange();
        }
        return resultString;
    }
}
