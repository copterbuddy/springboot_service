package com.copterbuddy.prototype.backend.exeption;

public class EmailException extends BaseException {

    public EmailException(String code) {
        super("email." + code);
    }

    public static EmailException templateNotFound(){
        return new EmailException("template.not.found");
    }
}
