package com.example.ex3.db;



import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ex3.MyApplication;
import com.example.ex3.entities.Contact;
import com.example.ex3.rooms.ContactsDao;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactsDB extends RoomDatabase{

    private static ContactsDB instance;
    public abstract ContactsDao contactsDao();

    public static synchronized ContactsDB getInstance() {
        if (instance == null) {
            Context context = MyApplication.getInstance().getApplicationContext();
            instance = Room.databaseBuilder(context, ContactsDB.class, "contacts_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
