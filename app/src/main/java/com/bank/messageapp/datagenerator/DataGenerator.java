package com.bank.messageapp.datagenerator;

import android.content.Context;

import com.bank.messageapp.persistence.AppDatabase;
import com.bank.messageapp.persistence.dao.ClientDao;
import com.bank.messageapp.persistence.dao.ClientServiceDataDao;
import com.bank.messageapp.persistence.dao.PushMessageDao;
import com.bank.messageapp.persistence.entity.Client;
import com.bank.messageapp.persistence.entity.ClientServiceData;
import com.bank.messageapp.persistence.entity.PushMessage;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    private Context context;

    private ClientDao clientDao;
    private PushMessageDao pushMessageDao;
    private ClientServiceDataDao clientServiceDataDao;

    public Client mClient;
    public List<PushMessage> mPushMessageList = new ArrayList<>();
    public ClientServiceData mClientServiceData;

    public DataGenerator(Context context) {
        this.context = context;
    }

    public void initGenerator() {
        clientDao = AppDatabase.getInstance(context).clientDao();
        pushMessageDao = AppDatabase.getInstance(context).pushMessageDao();
        clientServiceDataDao = AppDatabase.getInstance(context).clientServiceDataDao();
    }

    public void clearDB() {
        clientDao.deleteAllClients();
        clientServiceDataDao.deleteAllClientsServiceData();
        pushMessageDao.deleteAllPushMessages();
    }

    public void ClientGeneration() {
        mClient = new Client("1", "88005553535");
        clientDao.insertClient(mClient);
    }

    public void PushMessagesGeneration() {
        for (int i = 0; i < 10; i++) {
            PushMessage pushMessage = new PushMessage("Текст сообщения номер " + i, "Дата прихода сообщения", false, "1");
            mPushMessageList.add(pushMessage);
            pushMessageDao.insertPushMessages(pushMessage);
        }
    }

    public void isAuthorizedClientGeneration(Boolean value, String uuid, String fk_client) {
        mClientServiceData = new ClientServiceData(value, uuid, fk_client);
        clientServiceDataDao.insertClientServiceData(mClientServiceData);
    }

}
