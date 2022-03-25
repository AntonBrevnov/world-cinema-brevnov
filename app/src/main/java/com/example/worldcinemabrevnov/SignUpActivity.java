package com.example.worldcinemabrevnov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.worldcinemabrevnov.network.ApiHandler;
import com.example.worldcinemabrevnov.network.ErrorUtils;
import com.example.worldcinemabrevnov.network.models.SignUpBody;
import com.example.worldcinemabrevnov.network.services.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText mFirstNameInput;
    private EditText mLastNameInput;
    private EditText mEmailInput;
    private EditText mPasswordInput;
    private EditText mConfirmPasswordInput;
    private Button mSignInButton;
    private Button mSignUpButton;

    private ApiService service = ApiHandler.getInstance().getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        InitUI();
    }

    private void InitUI() {
        mFirstNameInput = findViewById(R.id.first_name_input);
        mLastNameInput = findViewById(R.id.last_name_input);
        mEmailInput = findViewById(R.id.email_input);
        mPasswordInput = findViewById(R.id.password_input);
        mConfirmPasswordInput = findViewById(R.id.confirm_password_input);

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignIUpButtonClick();
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

    private void handleSignIUpButtonClick() {
        AsyncTask.execute(() -> {
            String password = mPasswordInput.getText().toString();
            String confirm = mConfirmPasswordInput.getText().toString();

            if (password.equals(confirm)) {
                service.doSignUpRequest(getSignUpData()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                        } else if (response.code() == 400) {
                            String serverErrorMessage = ErrorUtils.parseError(response).message();
                            Toast.makeText(getApplicationContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Произошла неизвестная ошибка! Попробуйте позже", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Пароли не совпадают, повторите попытку!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private SignUpBody getSignUpData() {
        return new SignUpBody(
                mFirstNameInput.getText().toString(),
                mLastNameInput.getText().toString(),
                mEmailInput.getText().toString(),
                mPasswordInput.getText().toString()
        );
    }
}