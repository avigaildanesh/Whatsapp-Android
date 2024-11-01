package com.example.ex3.rooms;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ex3.entities.Contact;

import java.util.List;

@Dao
public interface ContactsDao {

    @Query("SELECT * FROM contact_table")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM contact_table WHERE id = :contactId")
    LiveData<Contact> getContactById(int contactId);

    @Insert
    void insert(Contact contact);

    @Insert
    void insertAll(List<Contact> contacts);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAll();
}
