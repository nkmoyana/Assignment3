package com.example.android.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.backendless.persistence.local.UserIdStorageFactory;

public class LoginActivity extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText etEmail, etPassword, etResetEmail;
    Button btnLogin, btnRegister;
    TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        etResetEmail = findViewById(R.id.etResetEmail);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.login_school));
        }

        tvLoad.setText(R.string.busy_checking_credentials_please_wait);
        showProgress(true);

        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {

                if(response)
                {
                    //get the user that's currently logged-in and authenticated
                    String userObjectId = UserIdStorageFactory.instance().getStorage().get();

                    Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                            ApplicationClass.user = response;
                            startActivity(new Intent(LoginActivity.this, Menu.class));
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(LoginActivity.this, getString(R.string.error) + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    });
                }
                else
                {
                    showProgress(false);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(LoginActivity.this, getString(R.string.error)  + fault.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etEmail.getText().toString().isEmpty() ||
                        etEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, getString(R.string.please_enter_all_fields), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String mail = etEmail.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();

                    tvLoad.setText(R.string.busy_checking_credentials_please_wait);
                    showProgress(true);

                    Backendless.UserService.login(mail, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                            ApplicationClass.user = response;
                            Toast.makeText(LoginActivity.this, getString(R.string.logged_in_successfully), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, Menu.class));
                            LoginActivity.this.finish();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(LoginActivity.this, getString(R.string.error) + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    }, true);

                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setMessage(getString(R.string.please_enter_the_email_address_related_to_the_account_you_want_to_reset_the_password_for)
                           + getString(R.string._a_reset_link_will_be_sent_to_the_email_address));

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_view,null);

                dialog.setView(dialogView);

                etResetEmail = dialogView.findViewById(R.id.etResetEmail);

                dialog.setPositiveButton(getString(R.string.reset), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(etResetEmail.getText().toString().isEmpty())
                        {
                            Toast.makeText(LoginActivity.this, getString(R.string.please_enter_school_email_address), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            tvLoad.setText(R.string.busy_sending_reset_instructions_to_email_address_please_wait);
                            showProgress(true);

                            Backendless.UserService.restorePassword(etResetEmail.getText().toString().trim(), new AsyncCallback<Void>() {
                                @Override
                                public void handleResponse(Void response) {
                                    
                                    showProgress(false);
                                    Toast.makeText(LoginActivity.this, R.string.reset_instructions_sent_to_email_address, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {

                                    showProgress(false);
                                    Toast.makeText(LoginActivity.this, getString(R.string.error) + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialog.show();

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