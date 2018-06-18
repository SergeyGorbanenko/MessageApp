package com.bank.messageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bank.messageapp.R;
import com.bank.messageapp.persistence.AppDatabase;
import com.bank.messageapp.persistence.datasource.LocalClientDataSource;
import com.bank.messageapp.persistence.datasource.LocalClientServiceDataSource;
import com.bank.messageapp.persistence.entity.Client;
import com.bank.messageapp.persistence.entity.ClientServiceData;
import com.bank.messageapp.retrofit.AcceptanceCodeRequest;
import com.bank.messageapp.retrofit.AcceptanceCodeResponse;
import com.bank.messageapp.retrofit.RegistrationRequest;
import com.bank.messageapp.retrofit.RegistrationResponse;
import com.bank.messageapp.retrofit.core.MessServerApi;
import com.bank.messageapp.retrofit.core.RetrofitBuilder;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Класс активности авторизации пользователя
 */
public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESS = "";
    /** Константа длина номера телефона  */
    public final static int LENGHT_PHONE_NUMBER = 10;

    /** Текстовое поле с меткой "Войти" */
    private TextView labelLogin;
    /** Текстовое поле с меткой +7 */
    private TextView textViewPlus7;
    /** Текстовое поле с информацией об ошибках */
    private TextView textViewInfo;
    /** Текстовое поле для ввода номера телефона или смс-кода подтверждения */
    private EditText editTextPhoneEnter;
    /** Кнопка "Войти" */
    private Button buttonLogin;

    /** Поле экземпляра интерфейса запросов к веб-серверу */
    private MessServerApi messServerApi;

    /** Поле локальное хранилище Клиентов */
    private LocalClientDataSource localClientDataSource;
    /** Поле локальное хранилище Сервис данных клиента */
    private LocalClientServiceDataSource localClientServiceDataSource;

    /** Поле уникальный идентификатор устройства */
    private String uuid;
    /** Поле номер телефона */
    private String phone;

    /**
     * инициализация при создании
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        AppDatabase db = AppDatabase.getInstance(this);
        localClientDataSource = new LocalClientDataSource(db.clientDao());
        localClientServiceDataSource = new LocalClientServiceDataSource(db.clientServiceDataDao());
        //
        labelLogin = findViewById(R.id.idLabelLogin);
        textViewInfo = findViewById(R.id.textViewInfo);
        editTextPhoneEnter = findViewById(R.id.editTextPhoneEnter);
        buttonLogin = findViewById(R.id.buttonLogin);
        //
        textViewPlus7 = findViewById(R.id.textViewPlus7);
        textViewPlus7.setVisibility(View.VISIBLE);
        textViewPlus7.setText("+7");

        //создаем коннект к серверу
        messServerApi = RetrofitBuilder.getInstance().create(MessServerApi.class);

        //Генерация данных в базу
        //DataGenerator dataGenerator = new DataGenerator(this);
        //dataGenerator.initGenerator();
        //dataGenerator.clearDB();
        //dataGenerator.ClientGeneration();
        //dataGenerator.PushMessagesGeneration("9237977175");
        //dataGenerator.isAuthorizedClientGeneration(false,);

        //Если есть один (и только один) авторизованный клиент, то сразу входим под ним
        List<ClientServiceData> clientServiceDataList = localClientServiceDataSource.getAllClientsServiceData();
        for (ClientServiceData clientServiceData : clientServiceDataList) {
            if (clientServiceData.getAuthorized())
                startActivity(new Intent(this, NavActivity.class)); //не инициализируем экран входа - переход дальше
        }

    }

    /**
     * Выполняет запрос на веб-сервер по регистрации нового клиента в системе
     * Выполняется при нажатии на кнопку "Войти", когда в поле EditText введен корректный номер телефона
     * @param view элемент управления - кнопка "Войти"
     */
    public void performLoginPhoneNumber(View view) {

        labelLogin.setText("Вход");

        textViewInfo.setText(R.string.textLogin1);
        editTextPhoneEnter.setHint(R.string.hintLogin1);

        //проверка на корректный номер
        if (editTextPhoneEnter.getText().toString().isEmpty())
            return;
        if (editTextPhoneEnter.getText().toString().length() != LENGHT_PHONE_NUMBER) {
            textViewInfo.setText("      " + textViewInfo.getText() + "\nНомер телефона должен содержать 10 цифр");
            return;
        }

        //создаем запрос
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.phone = editTextPhoneEnter.getText().toString();
        registrationRequest.uuid = getMacAddr();
        uuid = registrationRequest.uuid;
        phone = registrationRequest.phone;

        //выполняем запрос
        Call<RegistrationResponse> sendPhoneNumber = messServerApi.sendPhoneNumber(registrationRequest);
        buttonLogin.setEnabled(false);
        editTextPhoneEnter.setEnabled(false);
        sendPhoneNumber.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    //
                    editTextPhoneEnter.setHint(R.string.hintLogin2);
                    editTextPhoneEnter.setText(null);
                    textViewInfo.setText(R.string.textLogin2);
                    //editTextPhoneEnter.setText(response.body().sms_code);   //сразу ввели код, полученный от сервера
                    //
                    buttonLogin.setOnClickListener(v -> performLoginAcceptCode(v));
                    textViewPlus7.setVisibility(View.INVISIBLE);
                } else {
                    System.out.println("RESPONSE CODE " + response.code());
                    labelLogin.setText("Сервер не отвечает");
                    editTextPhoneEnter.setHint(R.string.hintLogin1);
                }
                buttonLogin.setEnabled(true);
                editTextPhoneEnter.setEnabled(true);
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                System.out.println("FAILURE " + t);
                labelLogin.setText("Сервер не отвечает");
                editTextPhoneEnter.setHint(R.string.hintLogin1);
                buttonLogin.setEnabled(true);
                editTextPhoneEnter.setEnabled(true);
            }
        });

    }

    /**
     * Выполняет запрос на веб-сервер по подтверждению смс-кода
     * Выполняется при нажатии на кнопку "Войти", когда в поле EditText введен корректный смс-код подтверждения
     * @param v элемент управления - кнопка "Войти"
     */
    public void performLoginAcceptCode(View v) {

        labelLogin.setText("Вход");

        //создаем запрос
        AcceptanceCodeRequest acceptanceCodeRequest = new AcceptanceCodeRequest();
        acceptanceCodeRequest.sms_code = editTextPhoneEnter.getText().toString();
        acceptanceCodeRequest.phone = phone;
        acceptanceCodeRequest.uuid = uuid;

        //выполняем запрос
        Call<AcceptanceCodeResponse> sendAcceptanceCode = messServerApi.sendAcceptanceCode(acceptanceCodeRequest);
        buttonLogin.setEnabled(false);
        editTextPhoneEnter.setEnabled(false);
        sendAcceptanceCode.enqueue(new Callback<AcceptanceCodeResponse>() {
            @Override
            public void onResponse(Call<AcceptanceCodeResponse> call, Response<AcceptanceCodeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().equals("NotMatch")) {  //Неверный код подтверждения
                        textViewInfo.setText(R.string.textLogin4);
                    } else {  //если код совпал
                        textViewInfo.setText(null);
                        editTextPhoneEnter.setHint(null);
                        editTextPhoneEnter.setText(null);
                        //
                        Client client = localClientDataSource.getClientByPhoneNumber(phone);    //Получили клиента по номеру телефона
                        if (client == null) {           //если клиента нет в базе
                            client = new Client(phone);                     //инит клиента
                            localClientDataSource.insertClient(client);     //записать в таблицу клиентов
                            localClientServiceDataSource.insertClientServiceData(new ClientServiceData(true, uuid, true, client.getId_client()));     //записать в сервис-таблицу флаг авторизации и uuid
                        } else {
                            //если такой клиент уже есть в базе, то только обновить флаг авторизации
                            List<ClientServiceData> clientServiceDataList = localClientServiceDataSource.getAllClientsServiceData();
                            for (ClientServiceData clientServiceData : clientServiceDataList)
                                if (clientServiceData.getFk_client().equals(client.getId_client())) {
                                    clientServiceData.setAuthorized(true);
                                    localClientServiceDataSource.updateClientServiceData(clientServiceData);
                                    break;
                                }
                        }
                        //
                        startActivity(new Intent(v.getContext(), NavActivity.class));
                    }
                } else {
                    System.out.println("RESPONSE CODE " + response.code());
                    labelLogin.setText("Сервер не отвечает");
                    editTextPhoneEnter.setHint(R.string.hintLogin2);
                }
                buttonLogin.setEnabled(true);
                editTextPhoneEnter.setEnabled(true);
            }

            @Override
            public void onFailure(Call<AcceptanceCodeResponse> call, Throwable t) {
                System.out.println("FAILURE " + t);
                labelLogin.setText("Сервер не отвечает");
                editTextPhoneEnter.setHint(R.string.hintLogin2);
                buttonLogin.setEnabled(true);
                editTextPhoneEnter.setEnabled(true);
            }
        });
    }

    /**
     * Выполняется при нажатии на кнопку "Назад"
     */
    @Override
    public void onBackPressed() {
        //Блокируем "назад"
    }

    /**
     * Получить mac-адрес утройства
     * @return mac-адрес утройства
     */
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    // res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "null";
    }

}
