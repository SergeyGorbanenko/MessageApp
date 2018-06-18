package com.bank.messageapp.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.messageapp.MyCustomApplication;
import com.bank.messageapp.PushMessageListAdapter;
import com.bank.messageapp.R;
import com.bank.messageapp.persistence.AppDatabase;
import com.bank.messageapp.persistence.Converters;
import com.bank.messageapp.persistence.datasource.LocalClientDataSource;
import com.bank.messageapp.persistence.datasource.LocalClientServiceDataSource;
import com.bank.messageapp.persistence.datasource.LocalPushMessageDataSource;
import com.bank.messageapp.persistence.entity.Client;
import com.bank.messageapp.persistence.entity.ClientServiceData;
import com.bank.messageapp.persistence.entity.PushMessage;
import com.bank.messageapp.retrofit.LogoutRequest;
import com.bank.messageapp.retrofit.LogoutResponse;
import com.bank.messageapp.retrofit.PushRequest;
import com.bank.messageapp.retrofit.PushResponse;
import com.bank.messageapp.retrofit.core.MessServerApi;
import com.bank.messageapp.retrofit.core.RetrofitBuilder;
import com.bank.messageapp.util.RecyclerTouchListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bank.messageapp.activities.MainActivity.getMacAddr;

public class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LocalPushMessageDataSource localPushMessageDataSource;
    private LocalClientDataSource localClientDataSource;
    private LocalClientServiceDataSource localClientServiceDataSource;

    private Client client;
    private ClientServiceData clientServiceData;

    private TextView clientHeaderName;
    private TextView clientHeaderPhoneNumber;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout mRelativeContainerForSetting;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private PushRequest pushRequest;
    private List<PushMessage> pushMessageList;

    private MessServerApi messServerApi;

    public static final String NOTIFICATION_CHANNEL_ID = "4565";

    private TextView textViewEmptyPushList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                clientHeaderPhoneNumber.setText(client.getPhone_number());
                super.onDrawerSlide(drawerView, slideOffset);
            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Инициализация источников данных
        AppDatabase db = AppDatabase.getInstance(this);
        localClientDataSource = new LocalClientDataSource(db.clientDao());
        localClientServiceDataSource = new LocalClientServiceDataSource(db.clientServiceDataDao());
        localPushMessageDataSource = new LocalPushMessageDataSource(db.pushMessageDao());

        //Инициализация авторизованного клиента
        List<ClientServiceData> clientServiceDataList = localClientServiceDataSource.getAllClientsServiceData();
        for (ClientServiceData clientServiceData : clientServiceDataList) {
            if (clientServiceData.getAuthorized()) {
                this.client = localClientDataSource.getClientObject(clientServiceData.getFk_client());
                this.clientServiceData = clientServiceData;
                break;
            }
        }

        View headerView = navigationView.getHeaderView(0);
        clientHeaderName = (TextView) headerView.findViewById(R.id.clientHeaderName);
        clientHeaderPhoneNumber = (TextView) headerView.findViewById(R.id.clientHeaderPhoneNumber);
        clientHeaderPhoneNumber.setText(client.getPhone_number());

        /////////////////////////////////////////////////////////////////////
        //создать канал уведомлений
        createNotificationChannel();

        //инициализировали метку
        textViewEmptyPushList = findViewById(R.id.textViewEmptyPushList);

        //создаем коннект к серверу
        messServerApi = RetrofitBuilder.getInstance().create(MessServerApi.class);

        //Инициализация списка сообщений
        pushMessageList = localPushMessageDataSource.getIsArchivedPushMessagesByClient(false, client.getId_client());

        //создаем запрос
        pushRequest = new PushRequest();
        pushRequest.phone = client.getPhone_number();

        mRelativeContainerForSetting = findViewById(R.id.relativeContainer);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutInNav);
        mRecyclerView = findViewById(R.id.pushmessage_recycler_view_in_nav);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PushMessageListAdapter(pushMessageList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new MyItemDividerDecorator(this, LinearLayoutManager.VERTICAL, 8));

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Toast.makeText(getApplicationContext(), pushMessageList.get(position).getText_message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Сообщение перемещено в архив!", Toast.LENGTH_SHORT).show();
                PushMessage pushMessage = localPushMessageDataSource.getPushMessage(pushMessageList.get(position).getId_pushmessage());
                pushMessage.setIsarchived_status(true);
                localPushMessageDataSource.updatePushMessages(pushMessage);
                pushMessageList.remove(position);
                mAdapter.notifyItemRemoved(position);
                //Скрыли метку, если список не пуст
                if (!pushMessageList.isEmpty())
                    textViewEmptyPushList.setVisibility(View.INVISIBLE);
                else
                    textViewEmptyPushList.setVisibility(View.VISIBLE);
            }
        }));

        //Подгрузили список с сервера
        if(!clientServiceData.getAutoupdate_push())
            getPushes();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        //Скрыли метку, если список не пуст
        if (!pushMessageList.isEmpty())
            textViewEmptyPushList.setVisibility(View.INVISIBLE);
        else
            textViewEmptyPushList.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //
        if (clientServiceData.getAutoupdate_push())     //Если в настройках включено автообновление
            getPushesPeriodic();
    }


    void refreshItems() {
        // Load items
        //if(!clientServiceData.getAutoupdate_push())
            getPushes();

        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }


    //Периодический запрос к серверу для получения списка сообщений
    public void getPushesPeriodic() {
        ((MyCustomApplication) this.getApplication()).getmDisposable().clear();
        ((MyCustomApplication) this.getApplication()).getmDisposable().add(
                messServerApi.getPushObservable(pushRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .repeatWhen(completed -> completed.delay(10, TimeUnit.SECONDS))
                        .subscribe(pushResponses -> {
                                    for (PushResponse pushResponse : pushResponses) {
                                        PushMessage pushMessage = new PushMessage(pushResponse.push, LocalDateTime.parse(pushResponse.date_delivered, Converters.formatter), false, false, client.getId_client());
                                        localPushMessageDataSource.insertPushMessages(pushMessage);
                                        pushMessageList.add(pushMessage);
                                        mAdapter.notifyItemInserted(mAdapter.getItemCount());
                                    }
                                    //
                                    if (pushResponses.size() > 0) {
                                        Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .setContentTitle("Алтайкапиталбанк")
                                                .setContentText("Новые сообщения: " + pushResponses.size())
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                .setContentIntent(pendingIntent)
                                                .setAutoCancel(true);

                                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                                        // notificationId is a unique int for each notification that you must define
                                        notificationManager.notify(1, mBuilder.build());
                                    }
                                    //Скрыли метку, если список не пуст
                                    if (!this.getTitle().equals("Настройки"))
                                        if (!pushMessageList.isEmpty())
                                            textViewEmptyPushList.setVisibility(View.INVISIBLE);
                                        else
                                            textViewEmptyPushList.setVisibility(View.VISIBLE);
                                },
                                Throwable::printStackTrace));
    }

    //Запрос к серверу для получения списка сообщений
    private void getPushes() {
        //выполняем запрос
        Call<List<PushResponse>> getPushes = messServerApi.getPush(pushRequest);
        getPushes.enqueue(new Callback<List<PushResponse>>() {
            @Override
            public void onResponse(Call<List<PushResponse>> call, Response<List<PushResponse>> response) {
                if (response.isSuccessful()) {
                    //
                    if (!response.body().isEmpty()) {
                        if (response.body().size() > 0) {
                            Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("Алтайкапиталбанк")
                                    .setContentText("Новые сообщения: " + response.body().size())
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true);

                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                            // notificationId is a unique int for each notification that you must define
                            notificationManager.notify(1, mBuilder.build());
                        }
                        for (PushResponse pushResponse : response.body()) {
                            PushMessage pushMessage = new PushMessage(pushResponse.push, LocalDateTime.parse(pushResponse.date_delivered, Converters.formatter), false, false, client.getId_client());
                            localPushMessageDataSource.insertPushMessages(pushMessage);
                            pushMessageList.add(pushMessage);
                            mAdapter.notifyItemInserted(mAdapter.getItemCount());
                        }
                        //Скрыли метку, если список не пуст
                            if (!pushMessageList.isEmpty())
                                textViewEmptyPushList.setVisibility(View.INVISIBLE);
                            else
                                textViewEmptyPushList.setVisibility(View.VISIBLE);
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

    //Создать канал уведомлений
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "df";
            String description = "df";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Создадим новый фрагмент
        Fragment fragment = null;
        Class fragmentClass = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bill_list) {
            setTitle(R.string.title_activity_nav);
            //refreshItems();
            clientServiceData = localClientServiceDataSource.getAuthorizedClientServiceData(true);
            if (clientServiceData.getAutoupdate_push())     //Если в настройках включено автообновление
                getPushesPeriodic();
            item.setChecked(true);
            //
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mRelativeContainerForSetting.setVisibility(View.INVISIBLE);
            //Скрыли метку, если список не пуст
            if (!pushMessageList.isEmpty())
                textViewEmptyPushList.setVisibility(View.INVISIBLE);
            else
                textViewEmptyPushList.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_archive){
            //Переход в архив
            Intent intent = new Intent(this, RecycleActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_setting) {
            setTitle(item.getTitle());
            fragmentClass = SettingsFragment.class;

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Вставляем фрагмент, заменяя текущий фрагмент
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativeContainer, fragment).commit();
            // Выделяем выбранный пункт меню в шторке
            item.setChecked(true);
            //
            mSwipeRefreshLayout.setVisibility(View.INVISIBLE);
            mRelativeContainerForSetting.setVisibility(View.VISIBLE);
            textViewEmptyPushList.setVisibility(View.INVISIBLE);

        /*} else if (id == R.id.nav_testing) {
            Intent intent = new Intent(this, TestingActivity.class);
            startActivity(intent);*/

        } else if (id == R.id.nav_logout) {     //реализовать выход из системы - по таблице - isAuthorized

            //создаем запрос
            LogoutRequest logoutRequest = new LogoutRequest();
            logoutRequest.uuid = getMacAddr();
            logoutRequest.phone = client.getPhone_number();

            //выполняем запрос
            Call<LogoutResponse> sendLogout = messServerApi.sendLogout(logoutRequest);
            sendLogout.enqueue(new Callback<LogoutResponse>() {
                @Override
                public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().answer.equals("YouIsLogout")) {
                            clientServiceData.setAuthorized(false);
                            localClientServiceDataSource.updateClientServiceData(clientServiceData);
                            //
                            ((MyCustomApplication) getApplication()).getmDisposable().clear();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    } else {
                        System.out.println("RESPONSE CODE " + response.code());
                        //тут модальное окно
                        Toast.makeText(getApplicationContext(), "Сервер не отвечает", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LogoutResponse> call, Throwable t) {
                    System.out.println("FAILURE " + t);
                    //тут модальное окно
                    Toast.makeText(getApplicationContext(), "Сервер не отвечает", Toast.LENGTH_LONG).show();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
