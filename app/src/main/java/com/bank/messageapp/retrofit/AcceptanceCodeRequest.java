package com.bank.messageapp.retrofit;

/**
 * Класс запроса на отправку смс-кода подтверждения
 */
public class AcceptanceCodeRequest {

    /**
     * Поле смс-код подтверждения
     */
    public String sms_code;
    /**
     * Поле уникальный идентификатор устройства
     */
    public String uuid;
    /**
     * Поле номер телефона
     */
    public String phone;
}
