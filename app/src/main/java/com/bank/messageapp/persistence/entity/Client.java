package com.bank.messageapp.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Класс сущности Клиент
 */
@Entity(tableName = "client")
public class Client {

    /** Поле ид клиента */
    @NonNull
    @PrimaryKey
    @ColumnInfo (name = "id_client")
    private String id_client;

    /** Поле имя */
    @ColumnInfo (name = "first_name")
    private String first_name;

    /** Поле фамилия */
    @ColumnInfo (name = "last_name")
    private String last_name;

    /** Поле номер телефона */
    @ColumnInfo (name = "phone_number")
    private String phone_number;

    /**
     * Создание нового клиента
     * @param id_client ид клиента
     * @param phone_number номер телефона
     */
    public Client(@NonNull String id_client, String phone_number) {
        this.id_client = id_client;
        this.first_name = "Нет данных";
        this.last_name = "Нет данных";
        this.phone_number = phone_number;
    }

    /**
     * Создание нового клиента
     * @param phone_number номер телефона
     */
    @Ignore
    public Client (String phone_number) {
        this.id_client = UUID.randomUUID().toString();
        this.first_name = "Нет данных";
        this.last_name = "Нет данных";
        this.phone_number = phone_number;
    }

    /**
     * Получить ид клиента
     * @return возвращает ид клиента
     */
    @NonNull
    public String getId_client() {
        return id_client;
    }

    /**
     * Задать ид клиета
     * @param id_client ид клиента
     */
    public void setId_client(@NonNull String id_client) {
        this.id_client = id_client;
    }

    /**
     * Получить имя клиента
     * @return возвращает имя клиента
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Задать имя клиета
     * @param first_name имя клиента
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Получить фамилию клиента
     * @return возвращает фамилию клиента
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Задать фамилия клиета
     * @param last_name фамилия клиента
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Получить номер телефона
     * @return возвращает номер телефона
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * Задать номер телефона
     * @param phone_number номер телефона
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
