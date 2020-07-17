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
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class RegisterActivity extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText etSchoolName, etSchoolEmail, etPassword, etRetype;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etSchoolName = findViewById(R.id.etSchoolName);
        etSchoolEmail = findViewById(R.id.etSchoolEmail);
        etPassword = findViewById(R.id.etPassword);
        etRetype = findViewById(R.id.etRetype);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etSchoolName.getText().toString().isEmpty() || etSchoolEmail.getText().toString().isEmpty() ||
                        etPassword.getText().toString().isEmpty() || etRetype.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, getString(R.string.please_enter_all_fields), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(etPassword.getText().toString().trim().equals(etRetype.getText().toString().trim()))
                    {
                        BackendlessUser user = new BackendlessUser();
                        user.setEmail(etSchoolEmail.getText().toString().trim());
                        user.setPassword(etPassword.getText().toString().trim());
                        user.setProperty("name", etSchoolName.getText().toString().trim());

                        tvLoad.setText(getString(R.string.loading_please_wait));
                        showProgress(true);

                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {

                                Toast.makeText(RegisterActivity.this, getString(R.string.user_successfully_registered), Toast.LENGTH_SHORT).show();
                                RegisterActivity.this.finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                                Toast.makeText(RegisterActivity.this, getString(R.string.error) + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, getString(R.string.make_sure_that_both_passwords_are_the_same), Toast.LENGTH_SHORT).show();
                    }
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