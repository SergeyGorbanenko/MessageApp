package com.bank.messageapp.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "client")
public class Client {

    @NonNull
    @PrimaryKey
    @ColumnInfo (name = "id_client")
    private String id_client;

    @ColumnInfo (name = "first_name")
    private String first_name;

    @ColumnInfo (name = "last_name")
    private String last_name;

    @ColumnInfo (name = "phone_number")
    private String phone_number;


    public Client(@NonNull String id_client, String phone_number) {
        this.id_client = id_client;
        this.first_name = "Нет данных";
        this.last_name = "Нет данных";
        this.phone_number = phone_number;
    }

    @Ignore
    public Client (String phone_number) {
        this.id_client = UUID.randomUUID().toString();
        this.first_name = "Нет данных";
        this.last_name = "Нет данных";
        this.phone_number = phone_number;
    }

    @NonNull
    public String getId_client() {
        return id_client;
    }

    public void setId_client(@NonNull String id_client) {
        this.id_client = id_client;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
