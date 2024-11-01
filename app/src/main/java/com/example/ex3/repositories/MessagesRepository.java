package com.example.ex3.repositories;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;

import com.example.ex3.MyApplication;
import com.example.ex3.api.ChatsAPI;
import com.example.ex3.db.MessagesDB;
import com.example.ex3.entities.Message;
import com.example.ex3.rooms.MessagesDao;

import java.util.List;

public class MessagesRepository {

    private MessagesDao messagesDao;
    private ChatsAPI chatsAPI;

    public MessagesRepository() {
        MessagesDB db = MessagesDB.getInstance();
        messagesDao = db.messagesDao();
        chatsAPI = new ChatsAPI();
    }

    public void fetchData(int id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        String token = sharedPreferences.getString("token", "");

        chatsAPI.getChatMessages(token, id, new ChatsAPI.GetChatMessagesCallback() {
            @Override
            public void onSuccess(List<Message> messages) {
                messagesDao.insertAll(messages);
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }


    public LiveData<List<Message>> getMessagesForContact(int contactId) {
        return messagesDao.getMessagesForContact(contactId);
    }

    public void sendMessage(int contactId, String content) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        String token = sharedPreferences.getString("token", "");
        chatsAPI.sendMessage(token,contactId, content, new ChatsAPI.SendMessageCallback() {
            @Override
            public void onSuccess() {
                // Message sent successfully
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle error
            }
        });
    }
}
