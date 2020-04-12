package com.example.codemystery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        overrideActionBar();
    }

    public void overrideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Code Mystery");
        actionBar.setIcon(R.drawable.baseline_lock_open_white_18dp);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void openActivity2(View view) {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void getFancyToast(View view){
        FancyToast.makeText(this, message + " " + counter, 2, shapes[counter-1], false).show();
        counter = counter < 6 ? ++counter : 1;
    }

    /*public void displayFancyButton(View view){
        getToast(message).show();
        getFancyToast(message).show();
    }*/

    /*public Toast getToast(String message){
        return Toast.makeText(this, message, Toast.LENGTH_LONG);
    }*/

    /*public FancyToast getFancyToast(String message){
        return FancyToast.makeText(this,message,FancyToast.LENGTH_LONG,FancyToast.WARNING,true);
    }*/
}
