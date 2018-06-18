package com.bank.messageapp.interfaces;

import com.bank.messageapp.persistence.entity.Client;

import java.util.List;

import io.reactivex.Flowable;

/**
 * интерфейс локального хранилища сущности Клиент
 */
public interface ClientDataSource {

    /**
     * Получить список всех клиентов
     */
    List<Client> getAllClients();

    /**
     * Получить клиента
     * @param id ид клиента
     */
    Flowable<Client> getClientFlowable(String id);

    /**
     * Получить клиента
     * @param id ид клиента
     */
    Client getClientObject(String id);

    /**
     * Вставить клиента
     * @param client клиент
     */
    void insertClient(Client client);

    /**
     * Обновить клиента
     * @param client клиент
     */
    void updateClient(Client client);

    /**
     * Удалить всех клиентов
     */
    void deleteAllClients();

    /**
     * Удалить клиента
     * @param client клиент
     */
    void deleteClient(Client client);

    /**
     * Получить клиента по номеру телефона
     * @param phone номер телефона
     */
    Client getClientByPhoneNumber(String phone);
}
