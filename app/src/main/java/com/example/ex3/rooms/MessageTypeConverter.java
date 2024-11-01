package com.example.ex3.rooms;

import androidx.room.TypeConverter;

import com.example.ex3.entities.Message;
import com.google.gson.Gson;

public class MessageTypeConverter {

    @TypeConverter
    public static Message fromString(String value) {
        return new Gson().fromJson(value, Message.class);
    }

    @TypeConverter
    public static String toString(Message message) {
        return new Gson().toJson(message);
    }
}
