package com.bank.messageapp;

import android.app.Application;

import io.reactivex.disposables.CompositeDisposable;

public class MyCustomApplication extends Application {

    private CompositeDisposable mDisposable;

    public CompositeDisposable getmDisposable() {
        return mDisposable;
    }

    public void setmDisposable(CompositeDisposable mDisposable) {
        this.mDisposable = mDisposable;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDisposable = new CompositeDisposable();
    }
}
