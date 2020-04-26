package com.example.codemystery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityScoreboard extends AppCompatActivity {

    TextView time;
    String spentTime;
    String timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        overrideActionBar();
        getScore();
    }

    public void getScore(){
        SharedPreferences getSeconds = this.getSharedPreferences("secondsCount", MODE_PRIVATE);
        spentTime = getSeconds.getString("seconds", "");
        time = findViewById(R.id.scoreTime);
        timeText = String.format("%1$s seconds", spentTime);
        time.setText(timeText);
    }

    public void overrideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Code Mystery - scoreboard");
        actionBar.setIcon(R.drawable.baseline_lock_open_white_18dp);
        actionBar.setDisplayShowHomeEnabled(true);
    }
}