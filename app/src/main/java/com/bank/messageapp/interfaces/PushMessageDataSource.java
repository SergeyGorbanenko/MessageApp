package com.bank.messageapp.interfaces;

import com.bank.messageapp.persistence.entity.PushMessage;

import java.util.List;

import io.reactivex.Flowable;

public interface PushMessageDataSource {

    List<PushMessage> getAllPushMessages();

    Flowable<List<PushMessage>> getAllPushMessagesFlowable();

    PushMessage getPushMessage(String id);

    void insertPushMessages(PushMessage... pushMessages);

    void updatePushMessages(PushMessage... pushMessages);

    void deleteAllPushMessages();

    void deletePushMessage(PushMessage pushMessage);

    List<PushMessage> getIsArchivedPushMessagesByClient(Boolean arch, String id_client);

}
