package com.bank.messageapp;

import android.app.Application;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Класс приложения
 */
public class MyCustomApplication extends Application {

    /**
     * Поле контейнер disposable-элементов
     */
    private CompositeDisposable mDisposable;

    /**
     * Получить контейнер disposable-элементов
     * @return контейнер disposable-элементов
     */
    public CompositeDisposable getmDisposable() {
        return mDisposable;
    }

    /**
     * Задать контейнер disposable-элементов
     * @param mDisposable контейнер disposable-элементов
     */
    public void setmDisposable(CompositeDisposable mDisposable) {
        this.mDisposable = mDisposable;
    }

    /**
     * инициализация при создании
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mDisposable = new CompositeDisposable();
    }
}
