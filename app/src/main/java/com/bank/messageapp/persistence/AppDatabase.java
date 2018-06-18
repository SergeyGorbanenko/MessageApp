package com.bank.messageapp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.bank.messageapp.persistence.dao.ClientDao;
import com.bank.messageapp.persistence.dao.ClientServiceDataDao;
import com.bank.messageapp.persistence.dao.PushMessageDao;
import com.bank.messageapp.persistence.entity.Client;
import com.bank.messageapp.persistence.entity.ClientServiceData;
import com.bank.messageapp.persistence.entity.PushMessage;

/**
 * Абстрактный класс создающий точку подключения к базе данных
 */
@Database(entities = {Client.class, PushMessage.class, ClientServiceData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Поле инстанс базы данных
     */
    private static volatile AppDatabase INSTANCE;

    /**
     * Поле dao клиента
     */
    public abstract ClientDao clientDao();
    /**
     * Поле dao сообщений
     */
    public abstract PushMessageDao pushMessageDao();
    /**
     * Поле dao сервис данных клиента
     */
    public abstract ClientServiceDataDao clientServiceDataDao();

    /**
     * Получить инстанс базы данных
     * @param context контекст приложения
     * @return инстанс базы данных
     */
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
