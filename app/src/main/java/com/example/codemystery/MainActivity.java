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
    EditText guessedNumberField;
    String generatedNumber, enteredNumber;
    String savedGeneratedNumber;
    static Timer timer;
    int count=0;
    boolean firstTimeAppOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        generatedNumber = getRandomNumberStringDefaultRange();
        Hint hint = new Hint(generatedNumber);

        hint1 = (TextView)findViewById(R.id.hint1);
        hint2 = (TextView)findViewById(R.id.hint2);
        hint3 = (TextView)findViewById(R.id.hint3);
        hint4 = (TextView)findViewById(R.id.hint4);

        hint1.setText(hint.oneCorrectWellPlaced());
        hint2.setText(hint.oneCorrectWrongPlaced());
        hint3.setText(hint.twoCorrectWrongPlaced());
        hint4.setText(hint.noneCorrect() + "\nactual: " + generatedNumber);

        //Log.i("CodeMystery-debug", "Actual: " + generatedNumber);
        //Log.i("CodeMystery-debug", "1good: " + hint.oneCorrectWellPlaced());
        //Log.i("CodeMystery-debug", "1wrong: " + hint.oneCorrectWrongPlaced());
        //Log.i("CodeMystery-debug", "2wrong: " + hint.twoCorrectWrongPlaced());
        //Log.i("CodeMystery-debug", "allwrong: " + hint.noneCorrect());

        if(!firstTimeAppOpen){
            loadPreviouslyGeneratedNumbers();
        } else {
            generateNewRandomNumbers();
        }
    }

    public void loadPreviouslyGeneratedNumbers() {
        SharedPreferences getNumbersFromMemory = this.getSharedPreferences("codeNumbers", MODE_PRIVATE);
        savedGeneratedNumber = getNumbersFromMemory.getString("savedGeneratedNumber", "");
        generatedNumber = savedGeneratedNumber;
    }

    public void generateNewRandomNumbers() {
        SharedPreferences keepNumbersInMemory = this.getSharedPreferences("codeNumbers", MODE_PRIVATE);
        SharedPreferences.Editor numbers = keepNumbersInMemory.edit();
        numbers.putString("savedGeneratedNumber", String.valueOf(generatedNumber));
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
            guessedNumberField = (EditText)findViewById(R.id.enteredNumber);
            enteredNumber = guessedNumberField.getText().toString();
            if(enteredNumber.equals(generatedNumber)){
                FancyToast.makeText(this, getString(R.string.attempt_solved), 2, FancyToast.SUCCESS, false).show();

                SharedPreferences secondsCount = this.getSharedPreferences("secondsCount", MODE_PRIVATE);
                SharedPreferences.Editor seconds = secondsCount.edit();
                seconds.putString("seconds", String.valueOf(count));
                seconds.apply();
                timer.cancel();
                guessedNumberField.getText().clear();
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
