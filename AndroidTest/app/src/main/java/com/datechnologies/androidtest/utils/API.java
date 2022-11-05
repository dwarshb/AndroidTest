package com.datechnologies.androidtest.utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    @GET("/Tests/scripts/chat_log.php")
    Call<DataWrapper> loadChat();

    @FormUrlEncoded
    @POST("/Tests/scripts/login.php")
    Call<DataWrapper> authenticate(@Field("email") String email,
                                   @Field("password") String password);
}
