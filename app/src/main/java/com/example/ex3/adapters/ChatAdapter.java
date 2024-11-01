package com.example.ex3.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex3.R;
import com.example.ex3.entities.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context context;
    private List<Message> messages;
    private String username;

    public ChatAdapter(Context context, String username) {
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        int reversePosition = messages.size() - 1 - position;
        Message message = messages.get(reversePosition);
        holder.messageContentTextView.setText(message.getContent());
        holder.messageTimeTextView.setText(message.getCreated());

        boolean isCurrentUserMessage = message.getSender().getUsername().equals(username);

        if (isCurrentUserMessage) {
            holder.itemView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            holder.messageBox.setBackgroundColor(ContextCompat.getColor(context, R.color.purple2));
        } else {
            holder.itemView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }
    }



    @Override
    public int getItemCount() {
        if (messages == null) {
            return 0;
        }
        return messages.size();
    }


    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageContentTextView;
        TextView messageTimeTextView;
        LinearLayout messageBox;

        ChatViewHolder(View itemView) {
            super(itemView);
            messageContentTextView = itemView.findViewById(R.id.messageContentTextView);
            messageTimeTextView = itemView.findViewById(R.id.messageTimeTextView);
            messageBox = itemView.findViewById(R.id.llMessageBox);

        }
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }
}
