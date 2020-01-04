package com.deyanm.shopy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private LinearLayout signInLayout, signUpLayout;
    private EditText logEmailEt, logPassEt, regEmailEt, regNameEt, regPassEt, regRepassEt;
    private Button displaySignUp, displaySignIn, loginBtn, regBtn;
    private TextView resetPasswordTv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initViewWidgets();
        displaySignUp.setOnClickListener(view -> {
            signInLayout.setVisibility(View.GONE);
            signUpLayout.setVisibility(View.VISIBLE);
        });
        displaySignIn.setOnClickListener(view -> {
            signUpLayout.setVisibility(View.GONE);
            signInLayout.setVisibility(View.VISIBLE);
        });
        loginBtn.setOnClickListener(view -> {
            String email = logEmailEt.getText().toString();
            String password = logPassEt.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        regBtn.setOnClickListener(v -> {
            String email = regEmailEt.getText().toString();
            String name = regNameEt.getText().toString();
            String password = regPassEt.getText().toString();
            String rePassword = regRepassEt.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter Emailfa", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(rePassword)) {
                Toast.makeText(getApplicationContext(), "Enter Password Again", Toast.LENGTH_SHORT).show();
                return;
            }
            if (email.contains("@") && password.equals(rePassword)) {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {

                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                        .setDisplayName(name)
//                                        .build();
//                                user.updateProfile(profileUpdates)
//                                        .addOnCompleteListener(task1 -> {
//                                            if (task1.isSuccessful()) {
//                                                Log.d(TAG, "User profile updated.");
//                                            }
//                                        });
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        });
            }
        });
    }

    private void initViewWidgets() {
        signInLayout = findViewById(R.id.signInLayout);
        signUpLayout = findViewById(R.id.signUpLayout);
        logEmailEt = findViewById(R.id.email_edt_text);
        logPassEt = findViewById(R.id.pass_edt_text);
        resetPasswordTv = findViewById(R.id.reset_pass_tv);
        regNameEt = findViewById(R.id.reg_name_edt);
        regEmailEt = findViewById(R.id.reg_email_edt);
        regPassEt = findViewById(R.id.reg_pass_edt);
        regRepassEt = findViewById(R.id.reg_repass_edt);
        displaySignUp = findViewById(R.id.signup_btn);
        displaySignIn = findViewById(R.id.displaySignIn_btn);
        loginBtn = findViewById(R.id.login_btn);
        regBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
