package com.example.ex3.model;

public class SendMessageRequest {
    private String msg;

    public SendMessageRequest(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }
}
