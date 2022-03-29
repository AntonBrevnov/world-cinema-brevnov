package com.example.worldcinemabrevnov.network;

import android.content.SharedPreferences;

import com.example.worldcinemabrevnov.data.DataManager;
import com.example.worldcinemabrevnov.network.services.ApiService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {
    private static ApiHandler mInstance;

    private Retrofit retrofit;

    public ApiHandler() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + DataManager.getToken()).build();
                        return chain.proceed(request);
                    }
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://cinema.areas.su/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ErrorUtils.retrofit = retrofit;
    }

    public static ApiHandler getInstance() {
        if (mInstance == null) {
            mInstance = new ApiHandler();
        }
        return mInstance;
    }

    public ApiService getService() {
        return retrofit.create(ApiService.class);
    }
}
