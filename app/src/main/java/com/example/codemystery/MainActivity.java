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

    TextView hint1, hint2, hint3, hint4;
    EditText guessedNumberField, notes;
    String generatedNumber, enteredNumber;
    String savedGeneratedNumber;
    String hintFirst, hintSecond, hintThird, hintFourth;
    static Timer timer;
    int count=0;
    boolean firstTimeAppOpen = true;
    SharedPreferences keepDataInMemory;
    SharedPreferences getDataFromMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notes = findViewById(R.id.notes);

        generateActionBar();
        startCodePuzzle();
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
        //Log.i("CodeMystery-debug", "Actual: " + generatedNumber);
        //Log.i("CodeMystery-debug", "1good: " + hint.oneCorrectWellPlaced());
        //Log.i("CodeMystery-debug", "1wrong: " + hint.oneCorrectWrongPlaced());
        //Log.i("CodeMystery-debug", "2wrong: " + hint.twoCorrectWrongPlaced());
        //Log.i("CodeMystery-debug", "allwrong: " + hint.noneCorrect());
        getDataFromMemory = this.getSharedPreferences("data", MODE_PRIVATE);
        firstTimeAppOpen = getDataFromMemory.getBoolean("hasAppBeenOpen", true);

        if(!firstTimeAppOpen){
            loadPreviouslyGeneratedNumbers();
        } else {
            generateNewRandomNumbers();
            firstTimeAppOpen = false;
            keepDataInMemory = this.getSharedPreferences("data", MODE_PRIVATE);
            SharedPreferences.Editor appFirstTimeOpen = keepDataInMemory.edit();
            appFirstTimeOpen.putBoolean("hasAppBeenOpen", firstTimeAppOpen);
            appFirstTimeOpen.apply();
        }
    }

    public void loadPreviouslyGeneratedNumbers() {
        getDataFromMemory = this.getSharedPreferences("data", MODE_PRIVATE);
        generatedNumber = getDataFromMemory.getString("savedGeneratedNumber", "");
        hintFirst = getDataFromMemory.getString("hintFirst", "");
        hintSecond = getDataFromMemory.getString("hintSecond", "");
        hintThird = getDataFromMemory.getString("hintThird", "");
        hintFourth = getDataFromMemory.getString("hintFourth", "");
        fillHints(generatedNumber, hintFirst, hintSecond, hintThird, hintFourth);
    }

    public void generateNewRandomNumbers() {
        keepDataInMemory = this.getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor numbers = keepDataInMemory.edit();
        generatedNumber = getRandomNumberStringDefaultRange();
        numbers.putString("savedGeneratedNumber", generatedNumber);
        Hint hint = new Hint(generatedNumber);
        hintFirst = hint.oneCorrectWellPlaced();
        numbers.putString("hintFirst", hintFirst);
        hintSecond = hint.oneCorrectWrongPlaced();
        numbers.putString("hintSecond", hintSecond);
        hintThird = hint.twoCorrectWrongPlaced();
        numbers.putString("hintThird", hintThird);
        hintFourth = hint.noneCorrect();
        numbers.putString("hintFourth", hintFourth);
        numbers.apply();

        fillHints(generatedNumber, hintFirst, hintSecond, hintThird, hintFourth);
        startTimer();
    }

    public void fillHints(String generatedNumber, String hintFirst, String hintSecond, String hintThird, String hintFourth) {
        hint1 = findViewById(R.id.hint1);
        hint2 = findViewById(R.id.hint2);
        hint3 = findViewById(R.id.hint3);
        hint4 = findViewById(R.id.hint4);
        hint1.setText(hintFirst);
        hint2.setText(hintSecond);
        hint3.setText(hintThird);
        hint4.setText(hintFourth + "\nactual: " + generatedNumber);
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
            guessedNumberField = (EditText)findViewById(R.id.enteredNumber);
            enteredNumber = guessedNumberField.getText().toString();
            if(enteredNumber.equals(generatedNumber)){
                FancyToast.makeText(this, getString(R.string.attempt_solved), 2, FancyToast.SUCCESS, false).show();
                SharedPreferences secondsCount = this.getSharedPreferences("secondsCount", MODE_PRIVATE);
                SharedPreferences.Editor seconds = secondsCount.edit();
                seconds.putString("seconds", String.valueOf(count));
                seconds.apply();
                guessedNumberField.getText().clear();
                notes.getText().clear();
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
