package com.bank.messageapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bank.messageapp.R;
import com.bank.messageapp.ui.ClientViewModel;
import com.bank.messageapp.ui.PushMessageViewModel;
import com.bank.messageapp.ui.ViewModelFactory;

import io.reactivex.disposables.CompositeDisposable;

public class TestingActivity extends AppCompatActivity {

    private static final String TAG = TestingActivity.class.getSimpleName();

    private TextView textViewFirstName;
    private TextView textViewLastName;
    private TextView textViewPhoneNumber;

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;

    private Button mButton;

    private ViewModelFactory mViewModelFactory;
    private ClientViewModel mClientViewModel;

    private PushMessageViewModel mPushMessageViewModel;

    private CompositeDisposable mDisposable = new CompositeDisposable();

    //
    private TextView textViewBillName;
    private EditText editTextBillName;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        ///////////////// Получение данных из интента
        Intent intent = getIntent();
        String mess = intent.getStringExtra(MainActivity.EXTRA_MESS);
        TextView textView = findViewById(R.id.textView);
        textView.setText(mess);
        /////////////////

        textViewFirstName = findViewById(R.id.textViewFirstName);
        textViewLastName = findViewById(R.id.textViewLastName);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        mButton = findViewById(R.id.button2);

/*        mViewModelFactory = Injection.provideModelFactory(this);
        mClientViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ClientViewModel.class);
        mButton.setOnClickListener(v -> { updateClientInfo(); });*/

        ///////////////////
        //для счетов
/*        textViewBillName = findViewById(R.id.textViewBillName);
        editTextBillName = findViewById(R.id.editTextBillName);
        button3 = findViewById(R.id.button3);

        mViewModelFactory2 = Injection.provideModelFactory2(this);
        mPushMessageViewModel = ViewModelProviders.of(this, mViewModelFactory2).get(PushMessageViewModel.class);
        button3.setOnClickListener(v -> updateBillInfo());*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Subscribe to the emissions of the client info from the view model.
        // Update the client info text view, at every onNext emission.
        // In case of error, log the exception.
/*        mDisposable.add(mClientViewModel.getClientFirstName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clientFirstName -> textViewFirstName.setText(clientFirstName),
                        throwable -> Log.e(TAG, "Unable to update first_name", throwable)));

        mDisposable.add(mClientViewModel.getClientLastName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clientLastName -> textViewLastName.setText(clientLastName),
                        throwable -> Log.e(TAG, "Unable to update last_name", throwable)));

        mDisposable.add(mClientViewModel.getClientPhoneNumber()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clientPhoneNumber -> textViewPhoneNumber.setText(clientPhoneNumber),
                        throwable -> Log.e(TAG, "Unable to update phone_number", throwable)));


        mDisposable.add(mClientViewModel.getClientFirstName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clientFirstName -> editTextFirstName.setText(clientFirstName),
                        throwable -> Log.e(TAG, "Unable to update first_name", throwable)));

        mDisposable.add(mClientViewModel.getClientLastName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clientLastName -> editTextLastName.setText(clientLastName),
                        throwable -> Log.e(TAG, "Unable to update last_name", throwable)));

        mDisposable.add(mClientViewModel.getClientPhoneNumber()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clientPhoneNumber -> editTextPhoneNumber.setText(clientPhoneNumber),
                        throwable -> Log.e(TAG, "Unable to update phone_number", throwable)));*/

    }

    @Override
    protected void onStop() {
        super.onStop();
        // clear all the subscriptions
        mDisposable.clear();
    }

/*    private void updateClientInfo() {
        String clientFirstName = editTextFirstName.getText().toString();
        String clientLastName = editTextLastName.getText().toString();
        String clientPhoneNumber = editTextPhoneNumber.getText().toString();
        // Disable the update button until the user name update has been done
        mButton.setEnabled(false);
        // Subscribe to updating the client info
        // Re-enable the button once the client info has been updated
        mDisposable.add(mClientViewModel.updateClientData(clientFirstName, clientLastName, clientPhoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> mButton.setEnabled(true),
                        throwable -> Log.e(TAG, "Unable to update client info", throwable)));
    }*/


/*    private void updateBillInfo() {
        String billName = editTextBillName.getText().toString();
        button3.setEnabled(false);
        //
        mDisposable.add(mPushMessageViewModel.updateBillData(billName, "---", 0.1, mClientViewModel.getClientID())       // mClientViewModel.getClientID()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> button3.setEnabled(true),
                        throwable -> Log.e(TAG, "Unable to update bill info", throwable)));
    }*/

}
