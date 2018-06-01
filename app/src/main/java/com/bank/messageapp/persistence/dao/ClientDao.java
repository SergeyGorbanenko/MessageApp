package com.bank.messageapp.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bank.messageapp.persistence.entity.Client;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ClientDao {

    //1
    @Query("SElECT * FROM client")
    List<Client> getAllClients();

    //2
    @Query("SElECT * FROM client WHERE id_client = :id LIMIT 1")
    Flowable<Client> getClientFlowable(String id);

    //3
    @Query("SElECT * FROM client WHERE id_client = :id LIMIT 1")
    Client getClientObject(String id);

    //4
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertClient(Client client);

    //5
    @Update
    void updateClient(Client client);

    //6
    @Query("DELETE FROM client")
    void deleteAllClients();

    //7
    @Delete
    void deleteClient(Client client);

    //8
    @Query("SELECT * FROM client WHERE phone_number = :phone LIMIT 1")
    Client getClientByPhoneNumber(String phone);

}
