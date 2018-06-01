package com.bank.messageapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bank.messageapp.PushMessageListAdapter;
import com.bank.messageapp.R;
import com.bank.messageapp.persistence.AppDatabase;
import com.bank.messageapp.persistence.dao.PushMessageDao;
import com.bank.messageapp.persistence.entity.PushMessage;
import com.bank.messageapp.util.MyItemDividerDecorator;
import com.bank.messageapp.util.RecyclerTouchListener;

import java.util.List;

public class RecycleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        //
        AppDatabase db = AppDatabase.getInstance(this);
        PushMessageDao pushMessageDao = db.pushMessageDao();
        List<PushMessage> pushMessageList = pushMessageDao.getAllPushMessages();
        //

        mRecyclerView = (RecyclerView) findViewById(R.id.pushmessage_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PushMessageListAdapter(pushMessageList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyItemDividerDecorator(this, LinearLayoutManager.VERTICAL, 16));

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) { }

            @Override
            public void onLongClick(View view, int position) { }
        }));
    }
}
