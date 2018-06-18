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

/**
 * интерфейс data access object сущности Клиент
 */
@Dao
public interface ClientDao {

    /**
     * Получить список всех клиентов
     */
    @Query("SElECT * FROM client")
    List<Client> getAllClients();

    /**
     * Получить клиента
     * @param id ид клиента
     */
    @Query("SElECT * FROM client WHERE id_client = :id LIMIT 1")
    Flowable<Client> getClientFlowable(String id);

    /**
     * Получить клиента
     * @param id ид клиента
     */
    @Query("SElECT * FROM client WHERE id_client = :id LIMIT 1")
    Client getClientObject(String id);

    /**
     * Вставить клиента
     * @param client клиент
     */
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertClient(Client client);

    /**
     * Обновить клиента
     * @param client клиент
     */
    @Update
    void updateClient(Client client);

    /**
     * Удалить всех клиентов
     */
    @Query("DELETE FROM client")
    void deleteAllClients();

    /**
     * Удалить клиента
     * @param client клиент
     */
    @Delete
    void deleteClient(Client client);

    /**
     * Получить клиента по номеру телефона
     * @param phone номер телефона
     */
    @Query("SELECT * FROM client WHERE phone_number = :phone LIMIT 1")
    Client getClientByPhoneNumber(String phone);

}
