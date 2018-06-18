package com.bank.messageapp.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Класс сущности Сервис данные клиента
 */
@Entity (tableName = "clientservicedata",
        indices = {@Index("fk_client")},
        foreignKeys = @ForeignKey(entity = Client.class,
                parentColumns = "id_client",
                childColumns = "fk_client",
                onDelete = ForeignKey.CASCADE))
public class ClientServiceData {

    /** Поле ид сервис данных клиента */
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id_csd")
    private String id_csd;

    /** Поле флаг авторизации */
    @ColumnInfo (name = "is_authorized")
    private Boolean isAuthorized;

    /** Поле уникальный идентификатор устройства */
    @ColumnInfo (name = "uuid")
    private String uuid;

    /** Поле флаг автополучения сообщений */
    @ColumnInfo (name = "autoupdate_push")
    private Boolean autoupdate_push;

    /** Поле ид клиента */
    @ColumnInfo(name = "fk_client")
    private String fk_client;

    /**
     * Создание новых сервис данных клиента
     * @param isAuthorized флаг авторизации
     * @param uuid уникальный идентификатор устройства
     * @param autoupdate_push флаг автополучения сообщений
     * @param fk_client ид клиента
     */
    @Ignore
    public ClientServiceData(Boolean isAuthorized, String uuid, Boolean autoupdate_push, String fk_client) {
        this.id_csd = UUID.randomUUID().toString();
        this.isAuthorized = isAuthorized;
        this.uuid = uuid;
        this.autoupdate_push = autoupdate_push;
        this.fk_client = fk_client;
    }

    /**
     * Создание новых сервис данных клиента
     * @param id_csd ид сервис данных клиента
     * @param isAuthorized флаг авторизации
     * @param uuid уникальный идентификатор устройства
     * @param autoupdate_push флаг автополучения сообщений
     * @param fk_client ид клиента
     */
    public ClientServiceData(@NonNull String id_csd, String uuid, Boolean isAuthorized, Boolean autoupdate_push, String fk_client) {
        this.id_csd = id_csd;
        this.isAuthorized = isAuthorized;
        this.uuid = uuid;
        this.autoupdate_push = autoupdate_push;
        this.fk_client = fk_client;
    }

    /**
     * Получить ид сервис данных клиента
     * @return возвращает ид сервис данных клиента
     */
    @NonNull
    public String getId_csd() {
        return id_csd;
    }

    /**
     * Задать ид сервис данных клиента
     * @param id_csd ид сервис данных клиента
     */
    public void setId_csd(@NonNull String id_csd) {
        this.id_csd = id_csd;
    }

    /**
     * Получить флаг авторизации
     * @return возвращает флаг авторизации
     */
    public Boolean getAuthorized() {
        return isAuthorized;
    }

    /**
     * Задать флаг авторизации
     * @param authorized флаг авторизации
     */
    public void setAuthorized(Boolean authorized) {
        isAuthorized = authorized;
    }

    /**
     * Получить уникальный идентификатор устройства
     * @return возвращает уникальный идентификатор устройства
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Задать уникальный идентификатор устройства
     * @param uuid уникальный идентификатор устройства
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Получить флаг автополучения сообщений
     * @return возвращает флаг автополучения сообщений
     */
    public Boolean getAutoupdate_push() {
        return autoupdate_push;
    }

    /**
     * Задать флаг автополучения сообщений
     * @param autoupdate_push флаг автополучения сообщений
     */
    public void setAutoupdate_push(Boolean autoupdate_push) {
        this.autoupdate_push = autoupdate_push;
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
