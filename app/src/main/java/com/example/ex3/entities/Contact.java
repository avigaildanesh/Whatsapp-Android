package com.example.ex3.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ex3.model.UserResponse;
import com.example.ex3.rooms.MessageTypeConverter;
import com.example.ex3.rooms.UserResponseTypeConverter;

@Entity(tableName = "contact_table")
@TypeConverters({UserResponseTypeConverter.class, MessageTypeConverter.class})
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private UserResponse user;

    @Nullable
    private Message lastMessage;


    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public Contact(UserResponse user, @Nullable Message lastMessage, int id) {
        this.user = user;
        this.lastMessage = lastMessage;
        this.id = id;
    }

    @Nullable
    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(@Nullable Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return user.getUsername();
    }

    public String getDisplayName() {
        return user.getDisplayName();
    }

    public String getProfilePic() {
        return user.getProfilePic();
    }
}
