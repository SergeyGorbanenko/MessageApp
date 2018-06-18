package com.bank.messageapp.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bank.messageapp.persistence.entity.ClientServiceData;

import java.util.List;

/**
 * интерфейс data access object сущности Сервис данные клиента
 */
@Dao
public interface ClientServiceDataDao {

    /**
     * Получить список всех сервис данных клиента
     */
    @Query("SElECT * FROM clientservicedata")
    List<ClientServiceData> getAllClientsServiceData();

    /**
     * Получить сервис данные клиента
     * @param id ид сервис данных клиента
     */
    @Query("SElECT * FROM clientservicedata WHERE id_csd = :id LIMIT 1")
    ClientServiceData getClientServiceData(String id);

    /**
     * Вставить сервис данные клиента
     * @param clientServiceData сервис данные клиента
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClientServiceData(ClientServiceData clientServiceData);

    /**
     * Обновить сервис данные клиента
     * @param clientServiceData сервис данные клиента
     */
    @Update
    void updateClientServiceData(ClientServiceData clientServiceData);

    /**
     * Удалить все сервис данные клиентов
     */
    @Query("DELETE FROM clientservicedata")
    void deleteAllClientsServiceData();

    /**
     * Удалить сервис данные клиента
     * @param clientServiceData сервис данные клиента
     */
    @Delete
    void deleteClientServiceData(ClientServiceData clientServiceData);

    /**
     * Получить сервис данные клиента по флагу авторизации
     * @param authorized флаг авторизации
     */
    @Query("SElECT * FROM clientservicedata WHERE is_authorized = :authorized LIMIT 1")
    ClientServiceData getAuthorizedClientServiceData(Boolean authorized);
}
