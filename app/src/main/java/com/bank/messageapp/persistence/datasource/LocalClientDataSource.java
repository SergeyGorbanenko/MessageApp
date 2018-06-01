package com.bank.messageapp.persistence.datasource;

import com.bank.messageapp.interfaces.ClientDataSource;
import com.bank.messageapp.persistence.dao.ClientDao;
import com.bank.messageapp.persistence.entity.Client;

import java.util.List;

import io.reactivex.Flowable;

public class LocalClientDataSource implements ClientDataSource{

    private final ClientDao mClientDao;

    public LocalClientDataSource(ClientDao ClientDao) {
        this.mClientDao = ClientDao;
    }

    @Override
    public List<Client> getAllClients() {
        return mClientDao.getAllClients();
    }

    @Override
    public Flowable<Client> getClientFlowable(String id) {
        return mClientDao.getClientFlowable(id);
    }

    @Override
    public Client getClientObject(String id) {
        return mClientDao.getClientObject(id);
    }

    @Override
    public void insertClient(Client client) {
        mClientDao.insertClient(client);
    }

    @Override
    public void updateClient(Client client) {
        mClientDao.updateClient(client);
    }

    @Override
    public void deleteAllClients() {
        mClientDao.deleteAllClients();
    }

    @Override
    public void deleteClient(Client client) {
        mClientDao.deleteClient(client);
    }

    @Override
    public Client getClientByPhoneNumber(String phone) {
        return mClientDao.getClientByPhoneNumber(phone);
    }
}
