package com.bank.messageapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
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
import com.bank.messageapp.retrofit.core.MessServerApi;
import com.bank.messageapp.retrofit.core.RetrofitBuilder;
import com.bank.messageapp.util.RecyclerTouchListener;

import java.util.List;

public class RecycleActivity extends AppCompatActivity {

    private LocalPushMessageDataSource localPushMessageDataSource;
    private LocalClientDataSource localClientDataSource;
    private LocalClientServiceDataSource localClientServiceDataSource;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Client client = null;

    private List<PushMessage> pushMessageList;

    private PushRequest pushRequest;
    private MessServerApi messServerApi;
    //private CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        AppDatabase db = AppDatabase.getInstance(this);
        localClientDataSource = new LocalClientDataSource(db.clientDao());
        localClientServiceDataSource = new LocalClientServiceDataSource(db.clientServiceDataDao());
        localPushMessageDataSource = new LocalPushMessageDataSource(db.pushMessageDao());

        //Инициализация клиента
        List<ClientServiceData> clientServiceDataList = localClientServiceDataSource.getAllClientsServiceData();
        for (ClientServiceData clientServiceData : clientServiceDataList) {
            if (clientServiceData.getAuthorized()) {
                client = localClientDataSource.getClientObject(clientServiceData.getFk_client());
                break;
            }
        }

        //создаем запрос
        pushRequest = new PushRequest();
        pushRequest.phone = client.getPhone_number();

        //создаем коннект к серверу
        messServerApi = RetrofitBuilder.getInstance().create(MessServerApi.class);

        //Инициализация списка сообщений
        pushMessageList = localPushMessageDataSource.getIsArchivedPushMessagesByClient(true, client.getId_client());

        mRecyclerView = findViewById(R.id.pushmessage_recycler_view_archive);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PushMessageListAdapter(pushMessageList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Toast.makeText(getApplicationContext(), pushMessageList.get(position).getText_message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Сообщение удалено!", Toast.LENGTH_SHORT).show();
                localPushMessageDataSource.deletePushMessage(pushMessageList.get(position));
                pushMessageList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        }));

        //Скрыли метку, если список не пуст
        TextView textViewEmptyArchiveList = findViewById(R.id.textViewEmptyArchiveList);
        if (!pushMessageList.isEmpty())
            textViewEmptyArchiveList.setVisibility(View.INVISIBLE);
        else
            textViewEmptyArchiveList.setVisibility(View.VISIBLE);


    }

}
