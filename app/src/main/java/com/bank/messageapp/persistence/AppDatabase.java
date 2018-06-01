package com.bank.messageapp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bank.messageapp.persistence.dao.ClientDao;
import com.bank.messageapp.persistence.dao.ClientServiceDataDao;
import com.bank.messageapp.persistence.dao.PushMessageDao;
import com.bank.messageapp.persistence.entity.Client;
import com.bank.messageapp.persistence.entity.ClientServiceData;
import com.bank.messageapp.persistence.entity.PushMessage;

@Database(entities = {Client.class, PushMessage.class, ClientServiceData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ClientDao clientDao();
    public abstract PushMessageDao pushMessageDao();
    public abstract ClientServiceDataDao clientServiceDataDao();

    public static AppDatabase  getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                               AppDatabase.class, "AppDatabase.db")
                               .allowMainThreadQueries()
                               .build();
                }
            }
        }
        return INSTANCE;
    }
}
