package com.bank.messageapp.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс сущности Сообщение
 */
@Entity (tableName = "pushmessage",
         indices = {@Index("fk_client")},
         foreignKeys = @ForeignKey(entity = Client.class,
                                    parentColumns = "id_client",
                                    childColumns = "fk_client",
                                    onDelete = ForeignKey.CASCADE))
public class PushMessage {

    /** Поле ид сообщения */
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id_pushmessage")
    private String id_pushmessage;

    /** Поле текст сообщения */
    @ColumnInfo (name = "text_message")
    private String text_message;

    /** Поле дата и время сообщения */
    @ColumnInfo (name = "date_acceptance")
    private LocalDateTime date_acceptance;

    /** Поле флаг прочтения сообщения */
    @ColumnInfo(name = "isread_status")
    private Boolean isread_status;

    /** Поле флаг архивирования сообщения*/
    @ColumnInfo(name = "isarchived_status")
    private Boolean isarchived_status;

    /** Поле ид клиента */
    @ColumnInfo(name = "fk_client")
    private String fk_client;

    /**
     * Создание нового сообщения
     * @param text_message текст сообщения
     * @param date_acceptance дата и время сообщения
     * @param isarchived_status флаг архивирования сообщения
     * @param fk_client ид клиента
     */
    @Ignore
    public PushMessage(String text_message, LocalDateTime date_acceptance, Boolean isread_status, Boolean isarchived_status, String fk_client) {
        this.id_pushmessage = UUID.randomUUID().toString();
        this.text_message = text_message;
        this.date_acceptance = date_acceptance;
        this.isread_status = isread_status;
        this.isarchived_status = isarchived_status;
        this.fk_client = fk_client;
    }

    /**
     * Создание нового сообщения
     * @param id_pushmessage ид сообщения
     * @param text_message текст сообщения
     * @param date_acceptance дата и время сообщения
     * @param isarchived_status флаг архивирования сообщения
     * @param fk_client ид клиента
     */
    public PushMessage(@NonNull String id_pushmessage, String text_message, LocalDateTime date_acceptance, Boolean isread_status, Boolean isarchived_status, String fk_client) {
        this.id_pushmessage = id_pushmessage;
        this.text_message = text_message;
        this.date_acceptance = date_acceptance;
        this.isread_status = isread_status;
        this.isarchived_status = isarchived_status;
        this.fk_client = fk_client;
    }

    /**
     * Получить ид сообщения
     * @return возвращает ид сообщения
     */
    @NonNull
    public String getId_pushmessage() {
        return id_pushmessage;
    }

    public void setId_pushmessage(@NonNull String id_pushmessage) {
        this.id_pushmessage = id_pushmessage;
    }

    /**
     * Получить ид сообщения
     * @return возвращает ид сообщения
     */
    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    /**
     * Получить дату и время сообщения
     * @return возвращает дату и время сообщения
     */
    public LocalDateTime getDate_acceptance() {
        return date_acceptance;
    }

    /**
     * Задать дату и время сообщения
     * @param date_acceptance дату и время сообщения
     */
    public void setDate_acceptance(LocalDateTime date_acceptance) {
        this.date_acceptance = date_acceptance;
    }

    /**
     * Получить флаг прочтения сообщения
     * @return возвращает флаг прочтения сообщения
     */
    public Boolean getIsread_status() {
        return isread_status;
    }

    /**
     * Задать флаг прочтения сообщения
     * @param isread_status флаг прочтения сообщения
     */
    public void setIsread_status(Boolean isread_status) {
        this.isread_status = isread_status;
    }

    /**
     * Получить флаг архивирования сообщения
     * @return возвращает флаг архивирования сообщения
     */
    public Boolean getIsarchived_status() {
        return isarchived_status;
    }

    /**
     * Задать флаг архивирования сообщения
     * @param isarchived_status флаг архивирования сообщения
     */
    public void setIsarchived_status(Boolean isarchived_status) {
        this.isarchived_status = isarchived_status;
    }

    /**
     * Получить ид клиента
     * @return возвращает ид клиента
     */
    public String getFk_client() {
        return fk_client;
    }

    /**
     * Задать ид клиета
     * @param fk_client ид клиента
     */
    public void setFk_client(String fk_client) {
        this.fk_client = fk_client;
    }

}
