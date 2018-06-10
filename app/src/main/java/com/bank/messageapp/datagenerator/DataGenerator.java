package com.bank.messageapp.datagenerator;

import android.content.Context;

import com.bank.messageapp.persistence.AppDatabase;
import com.bank.messageapp.persistence.dao.ClientDao;
import com.bank.messageapp.persistence.dao.ClientServiceDataDao;
import com.bank.messageapp.persistence.dao.PushMessageDao;
import com.bank.messageapp.persistence.entity.Client;
import com.bank.messageapp.persistence.entity.ClientServiceData;
import com.bank.messageapp.persistence.entity.PushMessage;

public class DataGenerator {

    private Context context;

    private ClientDao clientDao;
    private PushMessageDao pushMessageDao;
    private ClientServiceDataDao clientServiceDataDao;

    public Client mClient;
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

    public void PushMessagesGeneration(String phone) {
        pushMessageDao.deleteAllPushMessages();
        PushMessage pushMessage1 = new PushMessage("VISA1234 Cписание 536 р.\nБаланс: 500 р.", "23.05.2018", false, false, clientDao.getClientByPhoneNumber(phone).getId_client());
        PushMessage pushMessage2 = new PushMessage("VISA0200 Зачисление 1000 р.\nБаланс: 1000 р.", "24.05.2018", false, false, clientDao.getClientByPhoneNumber(phone).getId_client());
        PushMessage pushMessage3 = new PushMessage("VISA4321 Снятие наличных 100 р.\nБаланс: 0 р.", "25.05.2018", false, false, clientDao.getClientByPhoneNumber(phone).getId_client());
        pushMessageDao.insertPushMessages(pushMessage1);
        pushMessageDao.insertPushMessages(pushMessage2);
        pushMessageDao.insertPushMessages(pushMessage3);
    }

    public void isAuthorizedClientGeneration(Boolean value, String uuid, String fk_client) {
        mClientServiceData = new ClientServiceData(value, uuid, fk_client);
        clientServiceDataDao.insertClientServiceData(mClientServiceData);
    }

}
