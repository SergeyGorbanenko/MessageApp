package com.bank.messageapp.retrofit.core;

import com.bank.messageapp.retrofit.AcceptanceCodeRequest;
import com.bank.messageapp.retrofit.AcceptanceCodeResponse;
import com.bank.messageapp.retrofit.LogoutRequest;
import com.bank.messageapp.retrofit.LogoutResponse;
import com.bank.messageapp.retrofit.PushRequest;
import com.bank.messageapp.retrofit.PushResponse;
import com.bank.messageapp.retrofit.RegistrationRequest;
import com.bank.messageapp.retrofit.RegistrationResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * интерфейс для запросов к веб-серверу
 */
public interface MessServerApi {

    /**
     * Запрос на авторизацию пользователя в приложении
     * @param registrationRequest сформированный объект с данными для отправки
     * @see RegistrationRequest
     */
    @POST("clients/add")
    Call<RegistrationResponse> sendPhoneNumber(@Body RegistrationRequest registrationRequest);

    /**
     * Запрос на отправку смс-кода подтверждения
     * @param acceptanceCodeRequest сформированный объект с данными для отправки
     * @see AcceptanceCodeRequest
     */
    @POST("/clients/addsms")
    Call<AcceptanceCodeResponse> sendAcceptanceCode(@Body AcceptanceCodeRequest acceptanceCodeRequest);

    /**
     * Запрос на выход пользователя из системы
     * @param logoutRequest сформированный объект с данными для отправки
     * @see LogoutRequest
     */
    @POST("/clients/logout")
    Call<LogoutResponse> sendLogout(@Body LogoutRequest logoutRequest);

    /**
     * Запрос на получения списка сообщений
     * @param pushRequest сформированный объект с данными для отправки
     * @see PushRequest
     */
    @POST("/push")
    Call<List<PushResponse>> getPush(@Body PushRequest pushRequest);

    /**
     * Запрос на получения списка сообщений
     * @param pushRequest сформированный объект с данными для отправки
     * @see PushRequest
     */
    @POST("/push")
    Observable<List<PushResponse>> getPushObservable(@Body PushRequest pushRequest);

}
