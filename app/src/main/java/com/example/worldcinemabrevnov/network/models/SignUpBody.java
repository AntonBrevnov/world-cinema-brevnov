package com.example.worldcinemabrevnov.network.models;

import com.google.gson.annotations.SerializedName;

public class SignUpBody {
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
}
