package com.example.worldcinemabrevnov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText mFirstNameInput;
    private EditText mLastNameInput;
    private EditText mEmailInput;
    private EditText mPasswordInput;
    private EditText mConfirmPasswordInput;
    private Button mSignInButton;
    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        InitUI();
    }

    void InitUI() {
        mFirstNameInput = findViewById(R.id.first_name_input);
        mLastNameInput = findViewById(R.id.last_name_input);
        mEmailInput = findViewById(R.id.email_input);
        mPasswordInput = findViewById(R.id.password_input);
        mConfirmPasswordInput = findViewById(R.id.confirm_password_input);

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: sign in request
            }
        });

        mSignUpButton = findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }
}