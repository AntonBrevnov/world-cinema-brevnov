package com.example.worldcinemabrevnov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.worldcinemabrevnov.data.DataManager;
import com.example.worldcinemabrevnov.network.ApiHandler;
import com.example.worldcinemabrevnov.network.ErrorUtils;
import com.example.worldcinemabrevnov.network.models.SignInBody;
import com.example.worldcinemabrevnov.network.models.SignInResponse;
import com.example.worldcinemabrevnov.network.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmailInput;
    private EditText mPasswordInput;
    private Button mSignInButton;
    private Button mSignUpButton;

    private SharedPreferences localStorage;

    private ApiService service = ApiHandler.getInstance().getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        localStorage = getSharedPreferences("WCLS", MODE_PRIVATE);
        InitUI();

        checkToAuthorize();
    }

    private void InitUI() {
        mEmailInput = findViewById(R.id.email_input);
        mPasswordInput = findViewById(R.id.password_input);

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignInButtonClick();
            }
        });

        mSignUpButton = findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void checkToAuthorize() {
        if (!localStorage.getString("token", "").equals("")) {
            DataManager.setToken(localStorage.getString("token", ""));

            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }
    }

    private void handleSignInButtonClick() {
        AsyncTask.execute(() -> {
            service.doSignInRequest(getSignInData()).enqueue(new Callback<SignInResponse>() {
                @Override
                public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {

                    if (response.isSuccessful()) {

                        DataManager.setToken(response.body().getToken());
                        SharedPreferences.Editor editor = localStorage.edit();
                        editor.putString("token", response.body().getToken());
                        editor.commit();

                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    } else if (response.code() == 400) {
                        String serverErrorMessage = ErrorUtils.parseError(response).message();
                        Toast.makeText(getApplicationContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Произошла неизвестная ошибка! Попробуйте позже", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SignInResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private SignInBody getSignInData() {
        return new SignInBody(mEmailInput.getText().toString(), mPasswordInput.getText().toString());
    }
}