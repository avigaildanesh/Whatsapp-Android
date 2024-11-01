package com.example.ex3.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.ex3.MyApplication;
import com.example.ex3.entities.Chat;
import com.example.ex3.rooms.ChatsDao;



@Database(entities = {Chat.class}, version = 1)

public abstract class ChatsDB extends RoomDatabase {
    public abstract ChatsDao chatDao();

    private static volatile ChatsDB INSTANCE;

    public static ChatsDB getInstance() {
        if (INSTANCE == null) {
            Context context = MyApplication.getInstance().getApplicationContext();
            synchronized (ChatsDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ChatsDB.class, "chats_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private ChatsDao chatsDao;

        private PopulateDbAsync(ChatsDB db) {
            chatsDao = db.chatDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
