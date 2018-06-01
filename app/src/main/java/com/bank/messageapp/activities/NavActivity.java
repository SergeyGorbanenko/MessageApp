package com.bank.messageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bank.messageapp.R;
import com.bank.messageapp.persistence.AppDatabase;
import com.bank.messageapp.persistence.datasource.LocalClientDataSource;
import com.bank.messageapp.persistence.datasource.LocalClientServiceDataSource;
import com.bank.messageapp.persistence.entity.Client;
import com.bank.messageapp.persistence.entity.ClientServiceData;
import com.bank.messageapp.retrofit.LogoutRequest;
import com.bank.messageapp.retrofit.LogoutResponse;
import com.bank.messageapp.retrofit.core.MessServerApi;
import com.bank.messageapp.retrofit.core.RetrofitBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bank.messageapp.activities.MainActivity.getMacAddr;

public class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LocalClientDataSource localClientDataSource;
    private LocalClientServiceDataSource localClientServiceDataSource;

    private Client client;
    private ClientServiceData clientServiceData;

    private TextView clientHeaderName;
    private TextView clientHeaderPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Инициализация источников данных
        AppDatabase db = AppDatabase.getInstance(this);
        localClientDataSource = new LocalClientDataSource(db.clientDao());
        localClientServiceDataSource = new LocalClientServiceDataSource(db.clientServiceDataDao());
        //Инициализация авторизованного клиента
        List<ClientServiceData> clientServiceDataList = localClientServiceDataSource.getAllClientsServiceData();
        List<Client> clientList = localClientDataSource.getAllClients();
        for (ClientServiceData clientServiceData : clientServiceDataList) {
            if (clientServiceData.getAuthorized()) {
                for (Client client : clientList) {
                    if (client.getId_client().equals(clientServiceData.getFk_client())) {
                        this.client = client;
                        this.clientServiceData = clientServiceData;
                        break;
                    }
                }
                break;
            }
        }
        //

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
/*        DataGenerator dataGenerator = new DataGenerator(this);
        dataGenerator.initGenerator();
        dataGenerator.UserGeneration();
        dataGenerator.BillsGeneration();*/



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

        //
        View headerView = navigationView.getHeaderView(0);
        clientHeaderName = (TextView) headerView.findViewById(R.id.clientHeaderName);
        clientHeaderPhoneNumber = (TextView) headerView.findViewById(R.id.clientHeaderPhoneNumber);

        //
        clientHeaderPhoneNumber.setText(client.getPhone_number());

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

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
*//*        if (id == R.id.action_settings) {
            return true;
        }*//*

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bill_list) {
            Intent intent = new Intent(this, RecycleActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_setting) {
            //this.setTitle("Настройки");

        } else if (id == R.id.nav_testing) {
            Intent intent = new Intent(this, TestingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {     //реализовать выход из системы - по таблице - isAuthorized

            //создаем коннект к серверу
            MessServerApi messServerApi = RetrofitBuilder.getInstance().create(MessServerApi.class);

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
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    } else {
                        System.out.println("RESPONSE CODE " + response.code());
                        //тут модальное окно
                    }
                }

                @Override
                public void onFailure(Call<LogoutResponse> call, Throwable t) {
                    System.out.println("FAILURE " + t);
                    //тут модальное окно
                }
            });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
