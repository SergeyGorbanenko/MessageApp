package com.bank.messageapp.persistence.datasource;

import com.bank.messageapp.interfaces.PushMessageDataSource;
import com.bank.messageapp.persistence.dao.PushMessageDao;
import com.bank.messageapp.persistence.entity.PushMessage;

import java.util.List;

import io.reactivex.Flowable;

public class LocalPushMessageDataSource implements PushMessageDataSource {

    private final PushMessageDao mPushMessageDao;

    public LocalPushMessageDataSource(PushMessageDao pushMessageDao) {
        this.mPushMessageDao = pushMessageDao;
    }

    @Override
    public List<PushMessage> getAllPushMessages() {
        return mPushMessageDao.getAllPushMessages();
    }

    @Override
    public Flowable<List<PushMessage>> getAllPushMessagesFlowable() {
        return mPushMessageDao.getAllPushMessagesFlowable();
    }


    @Override
    public PushMessage getPushMessage(String id) {
        return mPushMessageDao.getPushMessage(id);
    }

    @Override
    public void insertPushMessages(PushMessage... pushMessages) {
        mPushMessageDao.insertPushMessages(pushMessages);
    }

    @Override
    public void updatePushMessages(PushMessage... pushMessages) {
        mPushMessageDao.updatePushMessages(pushMessages);
    }

    @Override
    public void deleteAllPushMessages() {
        mPushMessageDao.deleteAllPushMessages();
    }

    @Override
    public void deletePushMessage(PushMessage pushMessage) {
        mPushMessageDao.deletePushMessage(pushMessage);
    }

}
