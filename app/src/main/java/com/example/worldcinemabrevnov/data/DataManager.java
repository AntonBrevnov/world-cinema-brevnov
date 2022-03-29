package com.example.worldcinemabrevnov.data;

public class DataManager {
    private static String mToken;
    private static String mUserName;

    public static void setToken(String token) {
        mToken = token;
    }

    public static String getToken() {
        return mToken;
    }

    public static String getUserName() {
        return mUserName;
    }

    public static void setUserName(String mUserName) {
        DataManager.mUserName = mUserName;
    }
}
