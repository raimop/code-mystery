package com.example.codemystery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
    }

    public void openActivity3(View view) {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
    }
}