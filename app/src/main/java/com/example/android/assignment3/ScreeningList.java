package com.example.android.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ScreeningList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_list);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.screening_list));
        }

    }
}