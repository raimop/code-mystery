package com.example.codemystery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {

    private final String successMessage = "Yay! You have solved the puzzle";
    private final String failMessage = "Not the right code. Try again!";
    TextView randomOne, randomTwo, randomThree;
    EditText guessedOne, guessedTwo, guessedThree;
    int enteredFirst, enteredSecond, enteredThird;
    int firstRandom, secondRandom, thirdRandom;
    String firstNumberFromMemory, secondNumberFromMemory, thirdNumberFromMemory;
    boolean firstTimeAppOpen;

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
        randomOne = (TextView)findViewById(R.id.firstGeneratedNumber);
        randomTwo = (TextView)findViewById(R.id.secondGeneratedNumber);
        randomThree = (TextView)findViewById(R.id.thirdGeneratedNumber);
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
        randomOne.setText(firstNumberFromMemory);
        randomTwo.setText(secondNumberFromMemory);
        randomThree.setText(thirdNumberFromMemory);
        firstRandom = Integer.parseInt(firstNumberFromMemory);
        secondRandom = Integer.parseInt(secondNumberFromMemory);
        thirdRandom = Integer.parseInt(thirdNumberFromMemory);
    }

    public void generateNewRandomNumbers() {
        firstRandom = (int)(Math.random() * 10 + 1);
        secondRandom = (int)(Math.random() * 10 + 1);
        thirdRandom = (int)(Math.random() * 10 + 1);
        randomOne.setText(String.valueOf(firstRandom));
        randomTwo.setText(String.valueOf(secondRandom));
        randomThree.setText(String.valueOf(thirdRandom));
        SharedPreferences keepNumbersInMemory = this.getSharedPreferences("codeNumbers", MODE_PRIVATE);
        SharedPreferences.Editor edit = keepNumbersInMemory.edit();
        edit.putString("firstRandomValue", String.valueOf(firstRandom));
        edit.putString("secondRandomValue", String.valueOf(secondRandom));
        edit.putString("thirdRandomValue", String.valueOf(thirdRandom));
        edit.apply();
        firstTimeAppOpen = false;
    }

    public void submitCode(View view){
        guessedOne = (EditText)findViewById(R.id.firstEnteredDigit);
        guessedTwo = (EditText)findViewById(R.id.secondEnteredDigit);
        guessedThree = (EditText)findViewById(R.id.thirdEnteredDigit);
        enteredFirst = Integer.parseInt(guessedOne.getText().toString());
        enteredSecond = Integer.parseInt(guessedTwo.getText().toString());
        enteredThird = Integer.parseInt(guessedThree.getText().toString());
        if(firstRandom==enteredFirst && secondRandom==enteredSecond && thirdRandom==enteredThird){
            FancyToast.makeText(this, successMessage, 2, FancyToast.SUCCESS, false).show();
            generateNewRandomNumbers();
        } else {
            FancyToast.makeText(this, failMessage, 2, FancyToast.WARNING, false).show();
        }
    }
}
