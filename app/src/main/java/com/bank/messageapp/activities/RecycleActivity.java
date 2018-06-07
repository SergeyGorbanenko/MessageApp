package com.bank.messageapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bank.messageapp.PushMessageListAdapter;
import com.bank.messageapp.R;
import com.bank.messageapp.persistence.AppDatabase;
import com.bank.messageapp.persistence.datasource.LocalClientDataSource;
import com.bank.messageapp.persistence.datasource.LocalClientServiceDataSource;
import com.bank.messageapp.persistence.datasource.LocalPushMessageDataSource;
import com.bank.messageapp.persistence.entity.Client;
import com.bank.messageapp.persistence.entity.ClientServiceData;
import com.bank.messageapp.persistence.entity.PushMessage;
import com.bank.messageapp.retrofit.PushRequest;
import com.bank.messageapp.retrofit.PushResponse;
import com.bank.messageapp.retrofit.core.MessServerApi;
import com.bank.messageapp.retrofit.core.RetrofitBuilder;
import com.bank.messageapp.util.MyItemDividerDecorator;
import com.bank.messageapp.util.RecyclerTouchListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private LocalPushMessageDataSource localPushMessageDataSource;
    private LocalClientDataSource localClientDataSource;
    private LocalClientServiceDataSource localClientServiceDataSource;

    private Client client = null;

    private List<PushMessage> pushMessageList;

    private MessServerApi messServerApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        AppDatabase db = AppDatabase.getInstance(this);
        localClientDataSource = new LocalClientDataSource(db.clientDao());
        localClientServiceDataSource = new LocalClientServiceDataSource(db.clientServiceDataDao());
        localPushMessageDataSource = new LocalPushMessageDataSource(db.pushMessageDao());

        //создаем коннект к серверу
        messServerApi = RetrofitBuilder.getInstance().create(MessServerApi.class);

        //Инициализация клиента
        List<ClientServiceData> clientServiceDataList = localClientServiceDataSource.getAllClientsServiceData();
        for (ClientServiceData clientServiceData : clientServiceDataList) {
            if (clientServiceData.getAuthorized())
                client = localClientDataSource.getClientObject(clientServiceData.getFk_client());
        }

        //Инициализация списка сообщений
        localPushMessageDataSource = new LocalPushMessageDataSource(AppDatabase.getInstance(this).pushMessageDao());
        pushMessageList = localPushMessageDataSource.getAllPushMessages();
        //

        mRecyclerView = (RecyclerView) findViewById(R.id.pushmessage_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PushMessageListAdapter(pushMessageList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyItemDividerDecorator(this, LinearLayoutManager.VERTICAL, 16));

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Toast.makeText(getApplicationContext(), pushMessageList.get(position).getText_message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Удалено одно сообщение", Toast.LENGTH_SHORT).show();
                localPushMessageDataSource.deletePushMessage(pushMessageList.get(position));
                pushMessageList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        }));

        getNewPushes(getCurrentFocus());
    }

    public void getNewPushes(View v) {

        //создаем запрос
        PushRequest pushRequest = new PushRequest();
        pushRequest.phone = client.getPhone_number();

        //выполняем запрос
        Call<List<PushResponse>> getPushes = messServerApi.getPush(pushRequest);
        getPushes.enqueue(new Callback<List<PushResponse>>() {
            @Override
            public void onResponse(Call<List<PushResponse>> call, Response<List<PushResponse>> response) {
                if (response.isSuccessful()) {
                    //
                    if (!response.body().isEmpty()) {
                        for (PushResponse pushResponse : response.body()) {
                            PushMessage pushMessage = new PushMessage(pushResponse.push, pushResponse.date_delivered, false, client.getId_client());
                            localPushMessageDataSource.insertPushMessages(pushMessage);
                            pushMessageList.add(pushMessage);
                            mAdapter.notifyItemInserted(mAdapter.getItemCount());
                        }
                    }
                } else {
                    System.out.println("Сервер не отвечает");
                    System.out.println("RESPONSE CODE " + response.code());
                    Toast.makeText(getApplicationContext(), "Сервер не отвечает", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PushResponse>> call, Throwable t) {
                System.out.println("Сервер не отвечает");
                System.out.println("FAILURE " + t);
                Toast.makeText(getApplicationContext(), "Сервер не отвечает", Toast.LENGTH_LONG).show();
            }
        });


    }

}
