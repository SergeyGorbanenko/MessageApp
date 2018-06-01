package com.bank.messageapp.ui;

import android.arch.lifecycle.ViewModel;

public class ClientViewModel extends ViewModel {

/*    public final ClientDataSource mDataSource;

    public ClientViewModel(ClientDataSource DataSource) {
        this.mDataSource = DataSource;
    }

    private Client mClient;

    public String getClientID() {
        return mDataSource.getClientObject().getId_client();
    }

    public Flowable<String> getClientFirstName() {
        return mDataSource.getClient()
                .map(client -> {
                    mClient = client;
                    return client.getFirst_name();
        });
    }

    public Flowable<String> getClientLastName() {
        return mDataSource.getClient()
                .map(client -> {
                    mClient = client;
                    return client.getLast_name();
                });
    }

    public Flowable<String> getClientPhoneNumber() {
        return mDataSource.getClient()
                .map(client -> {
                    mClient = client;
                    return client.getPhone_number();
                });
    }

    public Completable updateClientData(final String clientFirstName, final String clientLastName, final String clientPhoneNumber) {
        return Completable.fromAction(() -> {
           mClient = mClient == null
                   ? new Client(clientFirstName, clientLastName, clientPhoneNumber)
                   : new Client(mClient.getId_client(), clientFirstName, clientLastName, clientPhoneNumber);
           mDataSource.updateClient(mClient);
        });
    }*/


}
