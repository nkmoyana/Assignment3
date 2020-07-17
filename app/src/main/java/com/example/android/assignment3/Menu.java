package com.example.android.assignment3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import static com.example.android.assignment3.ApplicationClass.user;

public class Menu extends AppCompatActivity {

    Button btnSeeAdd, btnScan, btnNewLearner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnSeeAdd = findViewById(R.id.btnSeeAll);
        btnScan = findViewById(R.id.btnScan);
        btnNewLearner = findViewById(R.id.btnNewLearner);

        String name = ApplicationClass.user.getProperty("name").toString().trim();
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle( name + " " + getString(R.string.school));
        }

        btnSeeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Menu.this, ScreeningList.class));

            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Menu.this, CovidScanner.class));

            }
        });

        btnNewLearner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Menu.this, CreateLearner.class));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.logout:
                Toast.makeText(this, getString(R.string.busy_signing_out_please_wait), Toast.LENGTH_LONG).show();

                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {

                        Toast.makeText(Menu.this, getString(R.string.user_signed_out_successfuly), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Menu.this, LoginActivity.class));
                        Menu.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(Menu.this, getString(R.string.error) + fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}