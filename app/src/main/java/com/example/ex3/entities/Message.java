package com.example.ex3.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ex3.model.UserResponse;
import com.example.ex3.rooms.MessageTypeConverter;
import com.example.ex3.rooms.UserResponseTypeConverter;

@Entity(tableName = "message_table")
@TypeConverters({UserResponseTypeConverter.class, MessageTypeConverter.class})
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String created;
    private UserResponse sender;
    private String content;

    public Message(int id,String created,UserResponse sender,String content) {
        this.sender = sender;
        this.created = created;
        this.id = id;
        this.content=content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCreated() {
        return created;
    }

    public UserResponse getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}