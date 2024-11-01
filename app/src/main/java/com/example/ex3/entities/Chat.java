package com.example.ex3.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@Entity(tableName = "Chats_table")
public class Chat {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String messagesJson;

    public Chat(int id, String messagesJson) {
        this.id = id;
        this.messagesJson = messagesJson;
    }

    public Chat(int id, List<Message> messages) {
        this.id = id;
        Gson gson = new Gson();
        this.messagesJson = gson.toJson(messages);
        int x = 1;
    }

    public String getMessagesJson() {
        return messagesJson;
    }

    public void setMessagesJson(String messagesJson) {
        this.messagesJson = messagesJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessages(List<Message> messages) {
        Gson gson = new Gson();
        this.messagesJson = gson.toJson(messages);
    }

    public List<Message> getMessages() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Message>>() {}.getType();
        return gson.fromJson(messagesJson, type);
    }
}
