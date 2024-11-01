package com.example.ex3.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ex3.rooms.UserResponseTypeConverter;

@Entity(tableName = "UserResponse_table")
@TypeConverters(UserResponseTypeConverter.class)
public class UserResponse {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String displayName;
    private String profilePic;

    public UserResponse() {
        // Empty constructor for Room
    }

    public UserResponse(String username, String displayName, String profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
