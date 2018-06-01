package com.bank.messageapp.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import io.reactivex.annotations.NonNull;

public class ViewModelFactory implements ViewModelProvider.Factory{

    @NonNull
    @Override
    public <T extends ViewModel> T create(@android.support.annotation.NonNull Class<T> modelClass) {
        return null;
    }

    /*    private final ClientDataSource mClientDataSource;

    public ViewModelFactory(ClientDataSource dataSource) {
        this.mClientDataSource = dataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClientViewModel.class)) {
            return (T) new ClientViewModel(mClientDataSource);
        } else
        throw new IllegalArgumentException("Unknown ViewModel class");
    }*/
}
