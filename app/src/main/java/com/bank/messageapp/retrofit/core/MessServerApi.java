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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessServerApi {

    @POST("clients/add")
    Call<RegistrationResponse> sendPhoneNumber(@Body RegistrationRequest registrationRequest);
    
    @POST("/clients/addsms")
    Call<AcceptanceCodeResponse> sendAcceptanceCode(@Body AcceptanceCodeRequest acceptanceCodeRequest);

    @POST("/clients/logout")
    Call<LogoutResponse> sendLogout(@Body LogoutRequest logoutRequest);

    @POST("/push")
    Call<List<PushResponse>> getPush(@Body PushRequest pushRequest);

}
