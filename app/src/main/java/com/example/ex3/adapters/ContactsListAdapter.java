package com.example.ex3.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex3.R;
import com.example.ex3.entities.Contact;
import com.example.ex3.entities.Message;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    private final LayoutInflater mInflater;
    private List<Contact> contacts;
    private OnItemClickListener itemClickListener;

    public ContactsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDisplayName;
        private final ImageView ivPic;
        private final TextView tvMessageContent;
        private final TextView tvDate;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvDisplayName = itemView.findViewById(R.id.tvDisplayName);
            ivPic = itemView.findViewById(R.id.ivPic);
            tvMessageContent = itemView.findViewById(R.id.tvMessageContent);
            tvDate = itemView.findViewById(R.id.tvDate);


            itemView.setOnClickListener(v -> {

                if (itemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Contact clickedContact = contacts.get(position);
                        itemClickListener.onItemClick(clickedContact);
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( ContactViewHolder holder, int position) {

        if (contacts == null){
            return;
        }
        final Contact current = contacts.get(position);
        holder.ivPic.setImageResource(R.drawable.minions);
        byte[] imageBytes = Base64.decode(current.getProfilePic(), Base64.DEFAULT);
       Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.ivPic.setImageBitmap(bitmap);
        Message lastMessage = current.getLastMessage();
        if (lastMessage != null) {
            holder.tvDate.setText(lastMessage.getCreated());
            holder.tvMessageContent.setText(lastMessage.getContent());
            holder.tvDisplayName.setText(current.getDisplayName());
        } else {
            holder.tvDate.setText("now it a good time");
            holder.tvMessageContent.setText("Say Hello");
            holder.tvDisplayName.setText(current.getDisplayName());
        }
    }

    @Override
    public int getItemCount() {
        if (contacts!=null){
            return contacts.size();
        }
        return 0;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
