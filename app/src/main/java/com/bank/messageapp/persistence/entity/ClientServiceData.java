package com.bank.messageapp.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity (tableName = "clientservicedata",
        indices = {@Index("fk_client")},
        foreignKeys = @ForeignKey(entity = Client.class,
                parentColumns = "id_client",
                childColumns = "fk_client",
                onDelete = ForeignKey.CASCADE))
public class ClientServiceData {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id_csd")
    private String id_csd;

    @ColumnInfo (name = "is_authorized")
    private Boolean isAuthorized;

    @ColumnInfo (name = "uuid")
    private String uuid;

    @ColumnInfo (name = "autoupdate_push")
    private Boolean autoupdate_push;

    @ColumnInfo(name = "fk_client")
    private String fk_client;

    @Ignore
    public ClientServiceData(Boolean isAuthorized, String uuid, Boolean autoupdate_push, String fk_client) {
        this.id_csd = UUID.randomUUID().toString();
        this.isAuthorized = isAuthorized;
        this.uuid = uuid;
        this.autoupdate_push = autoupdate_push;
        this.fk_client = fk_client;
    }

    public ClientServiceData(@NonNull String id_csd, String uuid, Boolean isAuthorized, Boolean autoupdate_push, String fk_client) {
        this.id_csd = id_csd;
        this.isAuthorized = isAuthorized;
        this.uuid = uuid;
        this.autoupdate_push = autoupdate_push;
        this.fk_client = fk_client;
    }

    @NonNull
    public String getId_csd() {
        return id_csd;
    }

    public void setId_csd(@NonNull String id_csd) {
        this.id_csd = id_csd;
    }

    public Boolean getAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(Boolean authorized) {
        isAuthorized = authorized;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getAutoupdate_push() {
        return autoupdate_push;
    }

    public void setAutoupdate_push(Boolean autoupdate_push) {
        this.autoupdate_push = autoupdate_push;
    }

    public String getFk_client() {
        return fk_client;
    }

    public void setFk_client(String fk_client) {
        this.fk_client = fk_client;
    }
}
