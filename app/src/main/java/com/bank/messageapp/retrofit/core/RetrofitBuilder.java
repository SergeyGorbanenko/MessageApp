package com.bank.messageapp.retrofit.core;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static Retrofit INSTANCE;

    public static Retrofit getInstance() {
        if (INSTANCE == null) {
            synchronized (Retrofit.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Retrofit.Builder()
                            .baseUrl("http://90.188.7.43:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
