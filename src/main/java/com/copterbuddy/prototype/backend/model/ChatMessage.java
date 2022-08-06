package com.copterbuddy.prototype.backend.model;

import lombok.Data;

import java.util.Date;


@Data
public class ChatMessage {

    public ChatMessage(){
        created = new Date();
    }

    private String from;
    private String message;
    private Date created;
}
