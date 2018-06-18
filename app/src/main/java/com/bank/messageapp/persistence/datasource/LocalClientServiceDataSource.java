package com.bank.messageapp.persistence.datasource;

import com.bank.messageapp.interfaces.ClientServiceDataSource;
import com.bank.messageapp.persistence.dao.ClientServiceDataDao;
import com.bank.messageapp.persistence.entity.ClientServiceData;

import java.util.List;
/**
 * Класс локального хранилища Сервис данных клиента
 */
public class LocalClientServiceDataSource implements ClientServiceDataSource {

    /**
     * Поле dao сервис данных клиента
     */
    public final ClientServiceDataDao mClientServiceDataDao;

    /**
     * Создание нового хранилища сервис данных клиента
     * @param clientServiceDataDao dao сервис данных клиента
     */
    public LocalClientServiceDataSource(ClientServiceDataDao clientServiceDataDao) {
        this.mClientServiceDataDao = clientServiceDataDao;
    }

    /**
     * Получить список всех сервис данных клиента
     * @return список сервис данных клиента
     */
    @Override
    public List<ClientServiceData> getAllClientsServiceData() {
        return mClientServiceDataDao.getAllClientsServiceData();
    }

    /**
     * Получить сервис данные клиента
     * @param id ид сервис данных клиента
     * @return сервис данные клиента
     */
    @Override
    public ClientServiceData getClientServiceData(String id) {
        return getClientServiceData(id);
    }

    /**
     * Вставить сервис данные клиента
     * @param clientServiceData сервис данные клиента
     */
    @Override
    public void insertClientServiceData(ClientServiceData clientServiceData) {
        mClientServiceDataDao.insertClientServiceData(clientServiceData);
    }

    /**
     * Обновить сервис данные клиента
     * @param clientServiceData сервис данные клиента
     */
    @Override
    public void updateClientServiceData(ClientServiceData clientServiceData) {
        mClientServiceDataDao.updateClientServiceData(clientServiceData);
    }

    /**
     * Удалить все сервис данные клиентов
     */
    @Override
    public void deleteAllClientsServiceData() {
        mClientServiceDataDao.deleteAllClientsServiceData();
    }

    /**
     * Удалить сервис данные клиента
     * @param clientServiceData сервис данные клиента
     */
    @Override
    public void deleteClientServiceData(ClientServiceData clientServiceData) {
        mClientServiceDataDao.deleteClientServiceData(clientServiceData);
    }

    /**
     * Получить сервис данные клиента по флагу авторизации
     * @param authorized флаг авторизации
     * @return сервис данные клиента
     */
    @Override
    public ClientServiceData getAuthorizedClientServiceData(Boolean authorized) {
        return mClientServiceDataDao.getAuthorizedClientServiceData(authorized);
    }
}
