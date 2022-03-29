package com.example.worldcinemabrevnov.network.models;

import com.google.gson.annotations.SerializedName;

public class SendMessageBody {
    @SerializedName("text")
    private String text;

    public void setText(String text) {
        this.text = text;
    }
}
