package com.example.ex3.rooms;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ex3.entities.Message;

import java.util.List;

@Dao
public interface MessagesDao {

    @Query("SELECT * FROM message_table")
    LiveData<List<Message>> getAllMessages();

    @Query("SELECT * FROM message_table WHERE id = :messageId")
    LiveData<Message> getMessageById(int messageId);

    @Insert
    void insert(Message message);

    @Insert
    void insertAll(List<Message> messages);

    @Update
    void update(Message message);

    @Delete
    void delete(Message message);

    @Query("DELETE FROM message_table")
    void deleteAll();

    @Query("SELECT * FROM message_table WHERE id = :contactId")
    LiveData<List<Message>> getMessagesForContact(int contactId);
}
