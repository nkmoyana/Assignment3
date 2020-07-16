package com.example.android.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RecordScreening extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_screening);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.screening);
        }
    }
}