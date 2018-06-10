package com.bank.messageapp.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bank.messageapp.persistence.entity.ClientServiceData;

import java.util.List;

@Dao
public interface ClientServiceDataDao {

    //1
    @Query("SElECT * FROM clientservicedata")
    List<ClientServiceData> getAllClientsServiceData();

    //2
    @Query("SElECT * FROM clientservicedata WHERE id_csd = :id LIMIT 1")
    ClientServiceData getClientServiceData(String id);

    //3
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClientServiceData(ClientServiceData clientServiceData);

    //4
    @Update
    void updateClientServiceData(ClientServiceData clientServiceData);

    //5
    @Query("DELETE FROM clientservicedata")
    void deleteAllClientsServiceData();

    //6
    @Delete
    void deleteClientServiceData(ClientServiceData clientServiceData);

    //7
    @Query("SElECT * FROM clientservicedata WHERE is_authorized = :authorized LIMIT 1")
    ClientServiceData getAuthorizedClientServiceData(Boolean authorized);
}
