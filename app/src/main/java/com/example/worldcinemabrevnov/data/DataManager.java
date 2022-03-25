package com.example.worldcinemabrevnov.data;

public class DataManager {
    private static String mToken;

    public static void setToken(String token) {
        mToken = token;
    }

    public static String getToken() {
        return mToken;
    }
}
