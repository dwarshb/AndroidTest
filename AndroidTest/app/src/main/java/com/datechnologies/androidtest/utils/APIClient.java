package com.datechnologies.androidtest.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Below class is used to initialize the Retrofit and set the required parameters such as
 * baseUrl and logging interceptor
 */
public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://dev.rapptrlabs.com") //Root url
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}