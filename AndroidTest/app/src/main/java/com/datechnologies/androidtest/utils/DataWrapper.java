package com.datechnologies.androidtest.utils;

import com.datechnologies.androidtest.api.ChatLogMessageModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class DataWrapper {

    @SerializedName("data")
    @Expose
    private List<ChatLogMessageModel> chats = null;

    public List<ChatLogMessageModel> getChats() {
        return chats;
    }

    public void setChats(List<ChatLogMessageModel> chats) {
        this.chats = chats;
    }

}