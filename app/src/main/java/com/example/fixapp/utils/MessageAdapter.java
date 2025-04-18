package com.example.fixapp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fixapp.R;
import com.example.fixapp.models.Message;

import java.text.DateFormat;
import java.util.List;

public class MessageAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_USER      = 0;
    private static final int TYPE_ASSISTANT = 1;

    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getSender().equals("user")
                ? TYPE_USER : TYPE_ASSISTANT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {

        int layout = (viewType == TYPE_USER)
                ? R.layout.chat_item_user
                : R.layout.chat_item_assistant;

        View v = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new RecyclerView.ViewHolder(v) {};
    }

    @Override
    public void onBindViewHolder(
            RecyclerView.ViewHolder holder, int pos) {

        Message m = messages.get(pos);
        TextView tvText = holder.itemView.findViewById(R.id.tvMessage);
        TextView tvTime = holder.itemView.findViewById(R.id.tvTime);
        tvText.setText(m.getText());
        tvTime.setText(
                DateFormat.getTimeInstance(DateFormat.SHORT)
                        .format(m.getTimestamp())
        );
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
