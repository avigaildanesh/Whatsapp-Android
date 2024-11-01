package com.example.ex3.rooms;

import androidx.room.TypeConverter;
import com.example.ex3.model.UserResponse;
import com.google.gson.Gson;

public class UserResponseTypeConverter {

    @TypeConverter
    public static UserResponse fromString(String value) {
        return new Gson().fromJson(value, UserResponse.class);
    }

    @TypeConverter
    public static String toString(UserResponse user) {
        return new Gson().toJson(user);
    }
}
