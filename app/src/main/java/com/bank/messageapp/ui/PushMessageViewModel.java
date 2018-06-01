package com.bank.messageapp.ui;

import android.arch.lifecycle.ViewModel;

import com.bank.messageapp.interfaces.PushMessageDataSource;

public class PushMessageViewModel extends ViewModel {

    public final PushMessageDataSource mDataSource;

    public PushMessageViewModel(PushMessageDataSource DataSource) {
        this.mDataSource = DataSource;
    }

/*    private PushMessage mPushMessage;
    private List<String> stringListName = new ArrayList<>();

    public Flowable<String> getBillName() {
        return mDataSource.getBill()
                .map(bill -> {
                mPushMessage = bill;
                return bill.getName();
            *//*for (PushMessage b : bills)
                stringListName.add(b.getName());
            return stringListName;*//*
        });
    }

    public Flowable<List<String>> getAllBillName() {
        stringListName.clear();
        return mDataSource.getAllBills()
                .map(bills -> {
                    //stringListName.clear();
                    for (PushMessage b : bills)
                        stringListName.add(b.getName());
                    return stringListName;
                });
    }

    public Completable updateBillData(final String billName, final String billNumber, final Double billBalance, final String fk_client) {
        return Completable.fromAction(() -> {
            mPushMessage = mPushMessage == null
                    ? new PushMessage(billName, billNumber, billBalance, fk_client)
                    : new PushMessage(mPushMessage.getId_bill(), billName, billNumber, billBalance, fk_client);
            mDataSource.insertOrUpdateBills(mPushMessage);
        });
    }*/
}
