package com.bank.messageapp.retrofit.core;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс создающий точку сетевого соединения
 */
public class RetrofitBuilder {

    /**
     * Поле инстанс сетевого соединения retrofit
     */
    private static Retrofit INSTANCE;

    /**
     * Получить инстанс сетевого соединения retrofit
     * @return инстанс сетевого соединения retrofit
     */
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
