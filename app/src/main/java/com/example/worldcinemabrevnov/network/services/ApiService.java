package com.example.worldcinemabrevnov.network.services;

import com.example.worldcinemabrevnov.network.models.SignInBody;
import com.example.worldcinemabrevnov.network.models.SignInResponse;
import com.example.worldcinemabrevnov.network.models.SignUpBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/auth/login")
    Call<SignInResponse> doSignInRequest(@Body SignInBody signInBody);

    @POST("/auth/register")
    Call<Void> doSignUpRequest(@Body SignUpBody signUpBody);
}
