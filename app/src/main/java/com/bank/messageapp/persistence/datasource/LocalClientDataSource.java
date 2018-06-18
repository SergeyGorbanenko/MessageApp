package com.bank.messageapp.persistence.datasource;

import com.bank.messageapp.interfaces.ClientDataSource;
import com.bank.messageapp.persistence.dao.ClientDao;
import com.bank.messageapp.persistence.entity.Client;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Класс локального хранилища Клиентов
 */
public class LocalClientDataSource implements ClientDataSource{

    /**
     * Поле dao клиентов
     */
    private final ClientDao mClientDao;

    /**
     * Создание нового хранилища клиентов
     * @param ClientDao dao клиентов
     */
    public LocalClientDataSource(ClientDao ClientDao) {
        this.mClientDao = ClientDao;
    }

    /**
     * Получить список всех клиентов
     * @return список клиентов
     */
    @Override
    public List<Client> getAllClients() {
        return mClientDao.getAllClients();
    }

    /**
     * Получить клиента
     * @param id ид клиента
     * @return клиент
     */
    @Override
    public Flowable<Client> getClientFlowable(String id) {
        return mClientDao.getClientFlowable(id);
    }

    /**
     * Получить клиента
     * @param id ид клиента
     * @return клиент
     */
    @Override
    public Client getClientObject(String id) {
        return mClientDao.getClientObject(id);
    }

    /**
     * Вставить клиента
     * @param client клиент
     */
    @Override
    public void insertClient(Client client) {
        mClientDao.insertClient(client);
    }

    /**
     * Обновить клиента
     * @param client клиент
     */
    @Override
    public void updateClient(Client client) {
        mClientDao.updateClient(client);
    }

    /**
     * Удалить всех клиентов
     */
    @Override
    public void deleteAllClients() {
        mClientDao.deleteAllClients();
    }

    /**
     * Удалить клиента
     * @param client клиент
     */
    @Override
    public void deleteClient(Client client) {
        mClientDao.deleteClient(client);
    }

    /**
     * Получить клиента по номеру телефона
     * @param phone номер телефона
     * @return клиент
     */
    @Override
    public Client getClientByPhoneNumber(String phone) {
        return mClientDao.getClientByPhoneNumber(phone);
    }
}
