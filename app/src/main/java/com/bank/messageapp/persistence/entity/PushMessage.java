package com.bank.messageapp.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity (tableName = "pushmessage",
         indices = {@Index("fk_client")},
         foreignKeys = @ForeignKey(entity = Client.class,
                                    parentColumns = "id_client",
                                    childColumns = "fk_client",
                                    onDelete = ForeignKey.CASCADE))
public class PushMessage {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id_pushmessage")
    private String id_pushmessage;

    @ColumnInfo (name = "text_message")
    private String text_message;

    @ColumnInfo (name = "date_acceptance")
    private String date_acceptance;

    @ColumnInfo(name = "isread_status")
    private Boolean isread_status;

    @ColumnInfo(name = "isarchived_status")
    private Boolean isarchived_status;

    @ColumnInfo(name = "fk_client")
    private String fk_client;

    @Ignore
    public PushMessage(String text_message, String date_acceptance, Boolean isread_status, Boolean isarchived_status, String fk_client) {
        this.id_pushmessage = UUID.randomUUID().toString();
        this.text_message = text_message;
        this.date_acceptance = date_acceptance;
        this.isread_status = isread_status;
        this.isarchived_status = isarchived_status;
        this.fk_client = fk_client;
    }

    public PushMessage(@NonNull String id_pushmessage, String text_message, String date_acceptance, Boolean isread_status, Boolean isarchived_status, String fk_client) {
        this.id_pushmessage = id_pushmessage;
        this.text_message = text_message;
        this.date_acceptance = date_acceptance;
        this.isread_status = isread_status;
        this.isarchived_status = isarchived_status;
        this.fk_client = fk_client;
    }

    @NonNull
    public String getId_pushmessage() {
        return id_pushmessage;
    }

    public void setId_pushmessage(@NonNull String id_pushmessage) {
        this.id_pushmessage = id_pushmessage;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public String getDate_acceptance() {
        return date_acceptance;
    }

    public void setDate_acceptance(String date_acceptance) {
        this.date_acceptance = date_acceptance;
    }

    public Boolean getIsread_status() {
        return isread_status;
    }

    public void setIsread_status(Boolean isread_status) {
        this.isread_status = isread_status;
    }

    public Boolean getIsarchived_status() {
        return isarchived_status;
    }

    public void setIsarchived_status(Boolean isarchived_status) {
        this.isarchived_status = isarchived_status;
    }

    public String getFk_client() {
        return fk_client;
    }

    public void setFk_client(String fk_client) {
        this.fk_client = fk_client;
    }

}
