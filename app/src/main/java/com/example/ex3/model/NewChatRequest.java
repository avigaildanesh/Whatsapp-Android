package com.example.ex3.model;

public class NewChatRequest {
    private String contactName;

    public NewChatRequest(String username) {
        this.contactName = username;
    }

    public String getUsername() {
        return contactName;
    }
}
