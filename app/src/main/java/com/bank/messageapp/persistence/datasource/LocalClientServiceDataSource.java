package com.bank.messageapp.persistence.datasource;

import com.bank.messageapp.interfaces.ClientServiceDataSource;
import com.bank.messageapp.persistence.dao.ClientServiceDataDao;
import com.bank.messageapp.persistence.entity.ClientServiceData;

import java.util.List;

public class LocalClientServiceDataSource implements ClientServiceDataSource {

    public final ClientServiceDataDao mClientServiceDataDao;

    public LocalClientServiceDataSource(ClientServiceDataDao clientServiceDataDao) {
        this.mClientServiceDataDao = clientServiceDataDao;
    }

    @Override
    public List<ClientServiceData> getAllClientsServiceData() {
        return mClientServiceDataDao.getAllClientsServiceData();
    }

    @Override
    public ClientServiceData getClientServiceData(String id) {
        return getClientServiceData(id);
    }

    @Override
    public void insertClientServiceData(ClientServiceData clientServiceData) {
        mClientServiceDataDao.insertClientServiceData(clientServiceData);
    }

    @Override
    public void updateClientServiceData(ClientServiceData clientServiceData) {
        mClientServiceDataDao.updateClientServiceData(clientServiceData);
    }

    @Override
    public void deleteAllClientsServiceData() {
        mClientServiceDataDao.deleteAllClientsServiceData();
    }

    @Override
    public void deleteClientServiceData(ClientServiceData clientServiceData) {
        mClientServiceDataDao.deleteClientServiceData(clientServiceData);
    }
}
