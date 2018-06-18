package com.bank.messageapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bank.messageapp.persistence.Converters;
import com.bank.messageapp.persistence.entity.PushMessage;

import java.util.List;
/**
 * Класс адаптер списка сообщений
 */
public class PushMessageListAdapter extends RecyclerView.Adapter<PushMessageListAdapter.ViewHolder> {

    /** Поле список Сообщений */
    private List<PushMessage> mPushMessageList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dot;
        public TextView pushmessage_text;
        public TextView pushmessage_dateacceptance;

        public ViewHolder (View view) {
            super(view);
            dot = view.findViewById(R.id.dot);
            pushmessage_text = view.findViewById(R.id.pushmessage_text);
            pushmessage_dateacceptance = view.findViewById(R.id.pushmessage_dateacceptance);
        }
    }

    public PushMessageListAdapter(List<PushMessage> mPushMessageList) {
        this.mPushMessageList = mPushMessageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PushMessage pushMessage = mPushMessageList.get(position);

        holder.dot.setText(Html.fromHtml("&#8226;"));
        //holder.dot.setText("");
        holder.pushmessage_text.setText(pushMessage.getText_message());
        holder.pushmessage_dateacceptance.setText(pushMessage.getDate_acceptance().format(Converters.formatter));
        if (pushMessage.getIsarchived_status())
            holder.pushmessage_text.setBackgroundResource(R.drawable.rounded_corner_archive);
        else
            holder.pushmessage_text.setBackgroundResource(R.drawable.rounded_corner);
    }

    @Override
    public int getItemCount() {
        return mPushMessageList.size();
    }
}
