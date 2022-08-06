package com.copterbuddy.prototype.backend.exeption;

public class ChatException  extends BaseException {

    public ChatException(String code) {
        super("chat." + code);
    }

    public static EmailException accessDenied(){
        return new EmailException("access.denied");
    }
}
