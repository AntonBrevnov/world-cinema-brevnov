package com.example.worldcinemabrevnov.network.services;

import com.example.worldcinemabrevnov.network.models.ChatResponse;
import com.example.worldcinemabrevnov.network.models.ChatsResponse;
import com.example.worldcinemabrevnov.network.models.MovieResponse;
import com.example.worldcinemabrevnov.network.models.SignInBody;
import com.example.worldcinemabrevnov.network.models.SignInResponse;
import com.example.worldcinemabrevnov.network.models.SignUpBody;
import com.example.worldcinemabrevnov.network.models.UserResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/auth/login")
    Call<SignInResponse> doSignInRequest(@Body SignInBody signInBody);

    @POST("/auth/register")
    Call<ResponseBody> doSignUpRequest(@Body SignUpBody signUpBody);

    @GET("/movies")
    Call<List<MovieResponse>> fetchMovies(@Query("filter") String filter);

    @GET("/user")
    Call<List<UserResponse>> fetchUserData();

    @GET("/user/chats")
    Call<List<ChatsResponse>> fetchChats();

    @GET("chats/{chatId}/messages")
    Call<List<ChatResponse>> fetchChat(@Path("chatId") String chatId);
}
