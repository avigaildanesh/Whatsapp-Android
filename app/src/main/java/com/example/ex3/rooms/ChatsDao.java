package com.example.ex3.rooms;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ex3.entities.Chat;


import java.util.List;

@Dao
public interface ChatsDao {

    @Insert
    void insert(Chat chat);

    @Query("SELECT * FROM Chats_table WHERE id = :id")
    LiveData<Chat> getChatById(int id);

    @Query("SELECT * FROM Chats_table")
    LiveData<List<Chat>> getAllChats();

    @Update
    void update(Chat chat);

    @Delete
    void delete(Chat chat);

    @Query("DELETE FROM Chats_table")
    void deleteAll();
}
