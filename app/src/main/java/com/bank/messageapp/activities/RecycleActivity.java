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
import com.bank.messageapp.util.RecyclerTouchListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Класс активности списка архивных сообщений
 */
public class RecycleActivity extends AppCompatActivity {

    /** Поле локальное хранилище Сообщений */
    private LocalPushMessageDataSource localPushMessageDataSource;
    /** Поле локальное хранилище Клиентов */
    private LocalClientDataSource localClientDataSource;
    /** Поле локальное хранилище Сервис данных клиента */
    private LocalClientServiceDataSource localClientServiceDataSource;

    /** Поле списка RecycleView */
    private RecyclerView mRecyclerView;
    /** Поле адаптера RecycleView */
    private RecyclerView.Adapter mAdapter;
    /** Поле LayoutManager RecycleView */
    private RecyclerView.LayoutManager mLayoutManager;

    /** Поле сущности Клиент */
    private Client client = null;

    /** Поле списка Сообщений */
    private List<PushMessage> pushMessageList;

    /**
     * инициализация при создании
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        AppDatabase db = AppDatabase.getInstance(this);
        localClientDataSource = new LocalClientDataSource(db.clientDao());
        localClientServiceDataSource = new LocalClientServiceDataSource(db.clientServiceDataDao());
        localPushMessageDataSource = new LocalPushMessageDataSource(db.pushMessageDao());

        List<ClientServiceData> clientServiceDataList = localClientServiceDataSource.getAllClientsServiceData();
        for (ClientServiceData clientServiceData : clientServiceDataList) {
            if (clientServiceData.getAuthorized()) {
                client = localClientDataSource.getClientObject(clientServiceData.getFk_client());
                break;
            }
        }

        //создаем запрос
        //pushRequest = new PushRequest();
        //pushRequest.phone = client.getPhone_number();

        //создаем коннект к серверу
        //messServerApi = RetrofitBuilder.getInstance().create(MessServerApi.class);

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

    /**
     * Показать архивные сообщения за последний месяц
     * Выполняется при нажатии на кнопку "За месяц"
     * @param v элемент управления - кнопка "За месяц"
     */
    public void showArchivePushByLastMonth(View v) {
        setTitle("Архив сообщений: за месяц");
        pushMessageList.clear();
        List<PushMessage> pushMessageListAll = localPushMessageDataSource.getIsArchivedPushMessagesByClient(true, client.getId_client());
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        for (PushMessage pm: pushMessageListAll) {
            if(pm.getDate_acceptance().getMonth().compareTo(nowLocalDateTime.getMonth()) == 0)
                pushMessageList.add(mAdapter.getItemCount(), pm);
        }
        mAdapter.notifyDataSetChanged();

    }

    /**
     * Показать архивные сообщения за последнюю неделю
     * Выполняется при нажатии на кнопку "За неделю"
     * @param v элемент управления - кнопка "За неделю"
     */
    public void showArchivePushByLastWeek(View v) {
        setTitle("Архив сообщений: за неделю");
        pushMessageList.clear();
        List<PushMessage> pushMessageListAll = localPushMessageDataSource.getIsArchivedPushMessagesByClient(true, client.getId_client());
        LocalDate nowLocalDate = LocalDate.now();
        for (PushMessage pm: pushMessageListAll) {
            //System.out.println(nowLocalDate.getDayOfYear() - pm.getDate_acceptance().toLocalDate().getDayOfYear());
            if(nowLocalDate.getDayOfYear() - pm.getDate_acceptance().toLocalDate().getDayOfYear() <= 7)
                pushMessageList.add(mAdapter.getItemCount(), pm);
        }
        mAdapter.notifyDataSetChanged();

    }

    /**
     * Показать архивные сообщения за текущий день
     * Выполняется при нажатии на кнопку "Сегодня"
     * @param v элемент управления - кнопка "Сегодня"
     */
    public void showArchivePushByToday(View v) {
        setTitle("Архив сообщений: сегодня");
        pushMessageList.clear();
        List<PushMessage> pushMessageListAll = localPushMessageDataSource.getIsArchivedPushMessagesByClient(true, client.getId_client());
        LocalDate nowLocalDate = LocalDate.now();
        for (PushMessage pm: pushMessageListAll) {
            //System.out.println(pm.getDate_acceptance().toLocalDate().getDayOfYear() + " " + nowLocalDate.getDayOfYear());
            if(pm.getDate_acceptance().toLocalDate().getDayOfYear() == nowLocalDate.getDayOfYear())
                pushMessageList.add(mAdapter.getItemCount(), pm);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Показать все архивные сообщения
     * Выполняется при нажатии на кнопку "Все"
     * @param v элемент управления - кнопка "Все"
     */
    public void showArchivePushByAll(View v) {
        setTitle("Архив сообщений");
        pushMessageList.clear();
        List<PushMessage> pushMessageListAll = localPushMessageDataSource.getIsArchivedPushMessagesByClient(true, client.getId_client());
        pushMessageList.addAll(pushMessageListAll);
        mAdapter.notifyDataSetChanged();

    }

}
