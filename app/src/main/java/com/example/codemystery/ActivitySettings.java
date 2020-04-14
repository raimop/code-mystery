package com.example.codemystery;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

public class ActivitySettings extends AppCompatActivity {

    private int counter = 1;
    private int[] shapes = new int[]{ FancyToast.ERROR,
            FancyToast.CONFUSING,
            FancyToast.WARNING,
            FancyToast.INFO,
            FancyToast.SUCCESS,
            FancyToast.DEFAULT };
    private final String message = "This is a test of the Emergency Broadcast System. This is only a test.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        overrideActionBar();
    }

    public void overrideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Code Mystery - settings");
        actionBar.setIcon(R.drawable.baseline_lock_open_white_18dp);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void getFancyToast(View view){
        FancyToast.makeText(this, message + " " + counter, 2, shapes[counter-1], false).show();
        counter = counter < 6 ? ++counter : 1;
    }

    @Override
    public void finish() {
        super.finish();
    }
}