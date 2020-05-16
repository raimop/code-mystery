package com.example.codemystery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    EditText guessedOne, guessedTwo, guessedThree;
    int enteredFirst, enteredSecond, enteredThird;
    int firstRandom, secondRandom, thirdRandom;
    String firstNumberFromMemory, secondNumberFromMemory, thirdNumberFromMemory;
    static Timer timer;
    int count=0;
    boolean firstTimeAppOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateActionBar();
        startCodePuzzle();

        String actualNumber = getRandomNumberStringDefaultRange();
        Hint hint = new Hint(actualNumber);

        Log.i("CodeMystery-debug", "Actual: " + actualNumber);
        Log.i("CodeMystery-debug", "1good: " + hint.oneCorrectWellPlaced());
        Log.i("CodeMystery-debug", "1wrong: " + hint.oneCorrectWrongPlaced());
        Log.i("CodeMystery-debug", "2wrong: " + hint.twoCorrectWrongPlaced());
        Log.i("CodeMystery-debug", "allwrong: " + hint.noneCorrect());
    }

    public void generateActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Code Mystery");
        actionBar.setIcon(R.drawable.baseline_lock_open_white_18dp);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    // for menu buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent intent = new Intent(this, ActivitySettings.class);
            startActivity(intent);
        } else if (id == R.id.scoreboard) {
            Intent intent = new Intent(this, ActivityScoreboard.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void startCodePuzzle(){
        if(!firstTimeAppOpen){
            loadPreviouslyGeneratedNumbers();
        } else {
            generateNewRandomNumbers();
        }
    }

    public void loadPreviouslyGeneratedNumbers() {
        SharedPreferences getNumbersFromMemory = this.getSharedPreferences("codeNumbers", MODE_PRIVATE);
        firstNumberFromMemory = getNumbersFromMemory.getString("firstRandomValue", "");
        secondNumberFromMemory = getNumbersFromMemory.getString("secondRandomValue", "");
        thirdNumberFromMemory = getNumbersFromMemory.getString("thirdRandomValue", "");
        firstRandom = Integer.parseInt(firstNumberFromMemory);
        secondRandom = Integer.parseInt(secondNumberFromMemory);
        thirdRandom = Integer.parseInt(thirdNumberFromMemory);
    }

    public void generateNewRandomNumbers() {
        firstRandom = (int)(Math.random() * 10 + 1);
        secondRandom = (int)(Math.random() * 10 + 1);
        thirdRandom = (int)(Math.random() * 10 + 1);
        SharedPreferences keepNumbersInMemory = this.getSharedPreferences("codeNumbers", MODE_PRIVATE);
        SharedPreferences.Editor numbers = keepNumbersInMemory.edit();
        numbers.putString("firstRandomValue", String.valueOf(firstRandom));
        numbers.putString("secondRandomValue", String.valueOf(secondRandom));
        numbers.putString("thirdRandomValue", String.valueOf(thirdRandom));
        numbers.apply();
        firstTimeAppOpen = false;
        startTimer();
    }

    public void startTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        count++;
                    }
                });
            }
        }, 1000, 1000);

    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    static String getRandomNumberStringDefaultRange(){
        int maxR = 999;
        int minR = 100;
        return Integer.toString(getRandomNumberInRange(minR, maxR));
    }

    public void submitCode(View view){
        try {
            guessedOne = (EditText)findViewById(R.id.firstEnteredDigit);
            guessedTwo = (EditText)findViewById(R.id.secondEnteredDigit);
            guessedThree = (EditText)findViewById(R.id.thirdEnteredDigit);
            enteredFirst = Integer.parseInt(guessedOne.getText().toString());
            enteredSecond = Integer.parseInt(guessedTwo.getText().toString());
            enteredThird = Integer.parseInt(guessedThree.getText().toString());
            if(firstRandom==enteredFirst && secondRandom==enteredSecond && thirdRandom==enteredThird){
                FancyToast.makeText(this, getString(R.string.attempt_solved), 2, FancyToast.SUCCESS, false).show();

                SharedPreferences secondsCount = this.getSharedPreferences("secondsCount", MODE_PRIVATE);
                SharedPreferences.Editor seconds = secondsCount.edit();
                seconds.putString("seconds", String.valueOf(count));
                seconds.apply();
                timer.cancel(); // not working???
                generateNewRandomNumbers();
            } else {
                FancyToast.makeText(this, getString(R.string.attempt_failed), 2, FancyToast.WARNING, false).show();
            }
        }
        catch (Exception ex){
            FancyToast.makeText(this, getString(R.string.attempt_error), 2, FancyToast.WARNING, false).show();
        }

    }
}
