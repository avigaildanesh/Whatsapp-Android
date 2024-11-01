package com.example.ex3.model;

public class RegistrationRequest {
    private String username;
    private String password;
    private String displayName;
    private String profilePic;

    public RegistrationRequest(String username, String password, String displayName,String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic=profilePic;
    }
}



