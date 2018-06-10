package com.bank.messageapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.bank.messageapp.MyCustomApplication;
import com.bank.messageapp.R;
import com.bank.messageapp.persistence.AppDatabase;
import com.bank.messageapp.persistence.datasource.LocalClientServiceDataSource;
import com.bank.messageapp.persistence.entity.ClientServiceData;

public class SettingsFragment extends Fragment {

    private LocalClientServiceDataSource localClientServiceDataSource;
    private ClientServiceData clientServiceData;

    private Switch mSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment, container, false);
        //
        AppDatabase db = AppDatabase.getInstance(getContext());
        localClientServiceDataSource = new LocalClientServiceDataSource(db.clientServiceDataDao());
        clientServiceData = localClientServiceDataSource.getAuthorizedClientServiceData(true);
        //Инициализация переключателя в настройках
        mSwitch = v.findViewById(R.id.switchAutoUpdate);
        if (clientServiceData.getAutoupdate_push())
            mSwitch.setChecked(true);
        else
            mSwitch.setChecked(false);
        //
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //включили
                    clientServiceData.setAutoupdate_push(true);
                    localClientServiceDataSource.updateClientServiceData(clientServiceData);
                } else {
                    //выключили
                    ((MyCustomApplication) getActivity().getApplication()).getmDisposable().clear();
                    clientServiceData.setAutoupdate_push(false);
                    localClientServiceDataSource.updateClientServiceData(clientServiceData);
                }
            }
        });

        return v;
    }

}
