package com.bank.messageapp.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bank.messageapp.persistence.entity.PushMessage;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PushMessageDao {

    //1
    @Query("SELECT * FROM PushMessage")
    List<PushMessage> getAllPushMessages();

    //2
    @Query("SELECT * FROM PushMessage")
    Flowable<List<PushMessage>> getAllPushMessagesFlowable();

    //3
    @Query("SELECT * FROM PushMessage WHERE id_pushmessage = :id LIMIT 1")
    PushMessage getPushMessage(String id);

    //4
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertPushMessages(PushMessage... pushMessages);

    //5
    @Update
    void updatePushMessages(PushMessage... pushMessage);

    //6
    @Query("DELETE FROM PushMessage")
    void deleteAllPushMessages();

    //7
    @Delete
    void deletePushMessage(PushMessage pushMessage);

    //8
    @Query("SELECT * FROM PushMessage WHERE isarchived_status = :arch AND fk_client = :id_client")
    List<PushMessage> getIsArchivedPushMessagesByClient(Boolean arch, String id_client);

}
