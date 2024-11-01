package com.example.ex3.db;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ex3.MyApplication;
import com.example.ex3.entities.Message;
import com.example.ex3.rooms.MessagesDao;

@Database(entities = {Message.class}, version = 1)
public abstract class MessagesDB extends RoomDatabase {

    private static MessagesDB instance;

    public abstract MessagesDao messagesDao();

    public static synchronized MessagesDB getInstance() {
        if (instance == null) {
            Context context = MyApplication.getInstance().getApplicationContext();
            instance = Room.databaseBuilder(context, MessagesDB.class, "messages_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}

