package com.datechnologies.androidtest.api;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * A data model that represents a chat log message fetched from the D & A Technologies Web Server.
 */

public class ChatLogMessageModel
{
    @SerializedName("user_id")
    private int userId;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("name")
    private String username;
    @SerializedName("message")
    private String message;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
