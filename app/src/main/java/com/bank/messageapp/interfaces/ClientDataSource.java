package com.bank.messageapp.interfaces;

import com.bank.messageapp.persistence.entity.Client;

import java.util.List;

import io.reactivex.Flowable;

public interface ClientDataSource {

    List<Client> getAllClients();

    Flowable<Client> getClientFlowable(String id);

    Client getClientObject(String id);

    void insertClient(Client client);

    void updateClient(Client client);

    void deleteAllClients();

    void deleteClient(Client client);

    Client getClientByPhoneNumber(String phone);
}
