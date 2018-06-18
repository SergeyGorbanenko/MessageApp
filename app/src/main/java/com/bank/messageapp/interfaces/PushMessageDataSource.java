package com.bank.messageapp.interfaces;

import com.bank.messageapp.persistence.entity.PushMessage;

import java.util.List;

import io.reactivex.Flowable;
/**
 * интерфейс локального хранилища сущности Сообщение
 */
public interface PushMessageDataSource {

    /**
     * Получить список всех сообщений
     */
    List<PushMessage> getAllPushMessages();

    /**
     * Получить список всех сообщений
     */
    Flowable<List<PushMessage>> getAllPushMessagesFlowable();

    /**
     * Получить сообщение
     * @param id ид сообщения
     */
    PushMessage getPushMessage(String id);

    /**
     * Вставить сообщения
     * @param pushMessages сообщения
     */
    void insertPushMessages(PushMessage... pushMessages);

    /**
     * Обновить сообщения
     * @param pushMessages сообщения
     */
    void updatePushMessages(PushMessage... pushMessages);

    /**
     * Удалить все сообщения
     */
    void deleteAllPushMessages();

    /**
     * Удалить сообщение
     * @param pushMessage сообщение
     */
    void deletePushMessage(PushMessage pushMessage);

    /**
     * Получить сообщения клиента по флагу архивирования
     * @param arch флаг архивирования
     * @param id_client ид клиента
     */
    List<PushMessage> getIsArchivedPushMessagesByClient(Boolean arch, String id_client);

}
