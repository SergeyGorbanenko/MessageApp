package com.bank.messageapp.persistence.datasource;

import com.bank.messageapp.interfaces.PushMessageDataSource;
import com.bank.messageapp.persistence.dao.PushMessageDao;
import com.bank.messageapp.persistence.entity.PushMessage;

import java.util.List;

import io.reactivex.Flowable;
/**
 * Класс локального хранилища Сообщений
 */
public class LocalPushMessageDataSource implements PushMessageDataSource {

    /**
     * Поле dao сообщений
     */
    private final PushMessageDao mPushMessageDao;

    /**
     * Создание нового хранилища сообщений
     * @param pushMessageDao dao сообщений
     */
    public LocalPushMessageDataSource(PushMessageDao pushMessageDao) {
        this.mPushMessageDao = pushMessageDao;
    }

    /**
     * Получить список всех сообщений
     * @return список сообщений
     */
    @Override
    public List<PushMessage> getAllPushMessages() {
        return mPushMessageDao.getAllPushMessages();
    }

    /**
     * Получить список всех сообщений
     * @return список сообщений
     */
    @Override
    public Flowable<List<PushMessage>> getAllPushMessagesFlowable() {
        return mPushMessageDao.getAllPushMessagesFlowable();
    }

    /**
     * Получить сообщение
     * @param id ид сообщения
     * @return сообщение
     */
    @Override
    public PushMessage getPushMessage(String id) {
        return mPushMessageDao.getPushMessage(id);
    }

    /**
     * Вставить сообщения
     * @param pushMessages сообщения
     */
    @Override
    public void insertPushMessages(PushMessage... pushMessages) {
        mPushMessageDao.insertPushMessages(pushMessages);
    }

    /**
     * Обновить сообщения
     * @param pushMessages сообщения
     */
    @Override
    public void updatePushMessages(PushMessage... pushMessages) {
        mPushMessageDao.updatePushMessages(pushMessages);
    }

    /**
     * Удалить все сообщения
     */
    @Override
    public void deleteAllPushMessages() {
        mPushMessageDao.deleteAllPushMessages();
    }

    /**
     * Удалить сообщение
     * @param pushMessage сообщение
     */
    @Override
    public void deletePushMessage(PushMessage pushMessage) {
        mPushMessageDao.deletePushMessage(pushMessage);
    }

    /**
     * Получить сообщения клиента по флагу архивирования
     * @param arch флаг архивирования
     * @param id_client ид клиента
     * @return сообщение
     */
    @Override
    public List<PushMessage> getIsArchivedPushMessagesByClient(Boolean arch, String id_client) {
        return mPushMessageDao.getIsArchivedPushMessagesByClient(arch, id_client);
    }
}
