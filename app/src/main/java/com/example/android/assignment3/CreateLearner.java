package com.example.android.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class CreateLearner extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText etLearnerName, etLearnerSurname, etGrade;
    TextView tvScan;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_learner);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etLearnerName = findViewById(R.id.etLearnerName);
        etLearnerSurname = findViewById(R.id.etLearnerSurname);
        etGrade = findViewById(R.id.etGrade);
        tvScan = findViewById(R.id.tvScan);
        btnSubmit = findViewById(R.id.btnSubmit);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.add_learner));
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etLearnerName.getText().toString().isEmpty() || etLearnerSurname.getText().toString().isEmpty() ||
                        etGrade.getText().toString().isEmpty() || tvScan.getText().toString().isEmpty())
                {
                    Toast.makeText(CreateLearner.this, getString(R.string.please_enter_all_fields), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Learner learner = new Learner();
                    learner.setName(etLearnerName.getText().toString().trim());
                    learner.setSurname(etLearnerSurname.getText().toString().trim());
                    learner.setGrade(etGrade.getText().toString().trim());
                    learner.setCode(tvScan.getText().toString().trim());
                    learner.setUserEmail(ApplicationClass.user.getEmail());

                    tvLoad.setText(getString(R.string.loading_please_wait));
                    showProgress(true);

                    Backendless.Persistence.save(learner, new AsyncCallback<Learner>() {
                        @Override
                        public void handleResponse(Learner response) {

                            Toast.makeText(CreateLearner.this, getString(R.string.new_learner_succssfully_saved), Toast.LENGTH_SHORT).show();
                            etLearnerName.setText(null);
                            etLearnerSurname.setText(null);
                            etGrade.setText(null);
                            tvScan.setText(null);
                            showProgress(false);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(CreateLearner.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    });
                }
            }
        });


    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}