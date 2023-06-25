package com.example.mortgagev0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static Mortgage mortgage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mortgage = new Mortgage(this);
    }

    public void modifyData(View v) {
        Intent myIntent = new Intent(this,DataActivity.class);
        this.startActivity(myIntent);
    }
}