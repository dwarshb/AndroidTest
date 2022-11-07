package com.datechnologies.androidtest.utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Below is interface includes two methods that calls API to server and get response.
 *
 * @see DataWrapper
 */
public interface API {
    /**
     * Below method triggers an API that fetches chat messages as response from the server.
     *
     * @return a response from the server that will be parsed with DataWrapper.
     */
    @GET("/Tests/scripts/chat_log.php")
    Call<DataWrapper> loadChat();

    /**
     * Below method triggers an API that will send email and password along with it and in return
     * give response whether its correct or not.
     *
     * @param email is an string value from an emailID field
     * @param password is a string value from a password field
     *
     * @return a response from the server that will be parsed with DataWrapper.
     */
    @FormUrlEncoded // annotation that used with POST type request
    @POST("/Tests/scripts/login.php")
    Call<DataWrapper> authenticate(@Field("email") String email,
                                   @Field("password") String password);
}
