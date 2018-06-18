package com.bank.messageapp.interfaces;

import com.bank.messageapp.persistence.entity.ClientServiceData;

import java.util.List;
/**
 * интерфейс локального хранилища сущности Сервис данные клиента
 */
public interface ClientServiceDataSource {

    /**
     * Получить список всех сервис данных клиента
     */
    List<ClientServiceData> getAllClientsServiceData();

    /**
     * Получить сервис данные клиента
     * @param id ид сервис данных клиента
     */
    ClientServiceData getClientServiceData(String id);

    /**
     * Вставить сервис данные клиента
     * @param clientServiceData сервис данные клиента
     */
    void insertClientServiceData(ClientServiceData clientServiceData);

    /**
     * Обновить сервис данные клиента
     * @param clientServiceData сервис данные клиента
     */
    void updateClientServiceData(ClientServiceData clientServiceData);

    /**
     * Удалить все сервис данные клиентов
     */
    void deleteAllClientsServiceData();

    /**
     * Удалить сервис данные клиента
     * @param clientServiceData сервис данные клиента
     */
    void deleteClientServiceData(ClientServiceData clientServiceData);

    /**
     * Получить сервис данные клиента по флагу авторизации
     * @param authorized флаг авторизации
     */
    ClientServiceData getAuthorizedClientServiceData(Boolean authorized);
}
