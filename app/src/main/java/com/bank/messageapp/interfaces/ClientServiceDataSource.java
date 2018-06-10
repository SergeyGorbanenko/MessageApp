package com.bank.messageapp.interfaces;

import com.bank.messageapp.persistence.entity.ClientServiceData;

import java.util.List;

public interface ClientServiceDataSource {

    List<ClientServiceData> getAllClientsServiceData();

    ClientServiceData getClientServiceData(String id);

    void insertClientServiceData(ClientServiceData clientServiceData);

    void updateClientServiceData(ClientServiceData clientServiceData);

    void deleteAllClientsServiceData();

    void deleteClientServiceData(ClientServiceData clientServiceData);

    ClientServiceData getAuthorizedClientServiceData(Boolean authorized);
}
