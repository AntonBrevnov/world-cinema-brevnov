package com.example.worldcinemabrevnov.network.models;

import com.google.gson.annotations.SerializedName;

public class ChatsResponse {
    @SerializedName("chatId")
    private String charId;
    @SerializedName("name")
    private String name;

    public String getCharId() {
        return charId;
    }

    public void setCharId(String charId) {
        this.charId = charId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
