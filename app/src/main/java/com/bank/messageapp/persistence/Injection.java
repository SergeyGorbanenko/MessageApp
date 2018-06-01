package com.bank.messageapp.persistence;

/*
Подключаем зависимости от источников данных
*/

import android.content.Context;

import com.bank.messageapp.interfaces.ClientDataSource;
import com.bank.messageapp.persistence.datasource.LocalClientDataSource;

public class Injection {

    public static ClientDataSource providerClientDataSource(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return new LocalClientDataSource(appDatabase.clientDao());
    }

/*    public static ViewModelFactory provideModelFactory(Context context) {
        ClientDataSource dataSource = providerClientDataSource(context);
        return new ViewModelFactory(dataSource);
    }*/

    //
}
