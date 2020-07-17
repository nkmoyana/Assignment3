package com.example.android.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CovidScanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_scanner);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.covid_scanner));
        }

    }
}