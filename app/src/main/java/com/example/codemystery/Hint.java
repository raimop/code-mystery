package com.example.codemystery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        while(true) {
            String resultString = MainActivity.getRandomNumberStringDefaultRange();
            boolean condition =
                    (resultString.charAt(0) == actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2)) ||
                    (resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) == actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2)) ||
                    (resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) == actualNumber.charAt(2));
            if(condition) return resultString;
        }
    }

    private String twoCorrectWellPlaced(){
        while(true) {
            String resultString = MainActivity.getRandomNumberStringDefaultRange();
            boolean condition =
                        (resultString.charAt(0) == actualNumber.charAt(0) && resultString.charAt(1) == actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2)) ||
                        (resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) == actualNumber.charAt(1) && resultString.charAt(2) == actualNumber.charAt(2)) ||
                        (resultString.charAt(0) == actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) == actualNumber.charAt(2));
            if(condition) return resultString;
        }
    }

    public String oneCorrectWrongPlaced(){
        while(true) {
            String resultString = shuffleString(oneCorrectWellPlaced());
            boolean condition = resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2);
            if(condition) return resultString;
        }
    }
    public String twoCorrectWrongPlaced(){
        while(true){
            String resultString = shuffleString(twoCorrectWellPlaced());
            boolean condition = resultString.charAt(0) != actualNumber.charAt(0) && resultString.charAt(1) != actualNumber.charAt(1) && resultString.charAt(2) != actualNumber.charAt(2);
            if(condition) return resultString;
        }
    }
    public String noneCorrect(){
        while(true){
            String resultString = MainActivity.getRandomNumberStringDefaultRange();
            boolean condition = resultString.matches("^[^" + actualNumber + "]+$") &&
                    resultString.charAt(0) != resultString.charAt(1) && resultString.charAt(1) != resultString.charAt(2) && resultString.charAt(0) != resultString.charAt(2);
            if(condition) return resultString;
        }
    }
}
