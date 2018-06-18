package com.bank.messageapp.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bank.messageapp.persistence.entity.PushMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * интерфейс data access object сущности Сообщение
 */
@Dao
public interface PushMessageDao {

    /**
     * Получить список всех сообщений
     */
    @Query("SELECT * FROM PushMessage")
    List<PushMessage> getAllPushMessages();

    /**
     * Получить список всех сообщений
     */
    @Query("SELECT * FROM PushMessage")
    Flowable<List<PushMessage>> getAllPushMessagesFlowable();

    /**
     * Получить сообщение
     * @param id ид сообщения
     */
    @Query("SELECT * FROM PushMessage WHERE id_pushmessage = :id LIMIT 1")
    PushMessage getPushMessage(String id);

    /**
     * Вставить сообщения
     * @param pushMessages сообщения
     */
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertPushMessages(PushMessage... pushMessages);

    /**
     * Обновить сообщения
     * @param pushMessage сообщения
     */
    @Update
    void updatePushMessages(PushMessage... pushMessage);

    /**
     * Удалить все сообщения
     */
    @Query("DELETE FROM PushMessage")
    void deleteAllPushMessages();

    /**
     * Удалить сообщение
     * @param pushMessage сообщение
     */
    @Delete
    void deletePushMessage(PushMessage pushMessage);

    /**
     * Получить сообщения клиента по флагу архивирования
     * @param arch флаг архивирования
     * @param id_client ид клиента
     */
    @Query("SELECT * FROM PushMessage WHERE isarchived_status = :arch AND fk_client = :id_client")
    List<PushMessage> getIsArchivedPushMessagesByClient(Boolean arch, String id_client);

}
