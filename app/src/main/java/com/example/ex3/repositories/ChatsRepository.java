package com.example.ex3.repositories;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ex3.api.ChatsAPI;
import com.example.ex3.db.ChatsDB;
import com.example.ex3.entities.Chat;
import com.example.ex3.entities.Contact;
import com.example.ex3.entities.Message;
import com.example.ex3.rooms.ChatsDao;
import com.example.ex3.rooms.ContactsDao;

import java.util.List;

public class ChatsRepository {

    private
    LiveData<List<Chat>> chats;
    private ChatsDao chatsDao;
    private String token;

    public ChatsRepository() {
        chatsDao = ChatsDB.getInstance().chatDao();
        chats = chatsDao.getAllChats();
    }

    public LiveData<List<Chat>> getAll() {
        return chats;
    }

    public LiveData<Chat> getById(int chatId) {

        return chatsDao.getChatById(chatId);
    }

    public LiveData<Chat> update(int id,String token) {
        LiveData<Chat> chatLiveData = chatsDao.getChatById(id);
        Chat chat = chatLiveData.getValue();
        if (chat != null) {
            chatsDao.update(chat);
        } else {
            insertById(id, token);
        }
        return chatsDao.getChatById(id);
    }

    public void delete(Chat chat) {
        chatsDao.delete(chat);
    }

    public void deleteAll() {
        chatsDao.deleteAll();
    }

    public void insertById(int id, String token) {

        ChatsAPI chatsAPI = new ChatsAPI();
        chatsAPI.getChatMessages(token, id, new ChatsAPI.GetChatMessagesCallback() {
            @Override
            public void onSuccess(List<Message> messages) {
                Chat chat = new Chat(id, messages);
                new DeleteChatAsync(chatsDao).execute(chat);
                new InsertChatAsync(chatsDao).execute(chat);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.i("messagesGet", "cant get messages");
            }
        });
    }

    public void insert(Chat chat) {
        chatsDao.insert(chat);
    }


    private static class InsertChatAsync extends AsyncTask<Chat, Void, Void> {

        private ChatsDao chatsDao;

        public InsertChatAsync(ChatsDao contactsDao) {
            this.chatsDao = contactsDao;
        }

        @Override
        protected Void doInBackground(Chat... chats) {
            chatsDao.insert(chats[0]);
            return null;
        }
    }

    private static class DeleteChatAsync extends AsyncTask<Chat, Void, Void> {

        private ChatsDao chatsDao;

        public DeleteChatAsync(ChatsDao contactsDao) {
            this.chatsDao = contactsDao;
        }

        @Override
        protected Void doInBackground(Chat... chats) {
            chatsDao.delete(chats[0]);
            return null;
        }
    }
}
