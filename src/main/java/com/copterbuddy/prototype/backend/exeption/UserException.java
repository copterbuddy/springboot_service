package com.copterbuddy.prototype.backend.exeption;

public class UserException extends BaseException {

    public UserException(String code) {
        super("user." + code);
    }

    public static UserException requestNull() {
        return new UserException("register.request.null");
    }

    public static UserException emailNull() {
        return new UserException("register.email.null");
    }

    public static UserException unauthorized() {
        return new UserException("unauthorized");
    }
    public static UserException notFound() {
        return new UserException("user.not.found");
    }

    //CREATE
    public static UserException createEmailNull() {
        return new UserException("register.email.null");
    }
    public static UserException createEmailDuplicated() {
        return new UserException("register.email.duplicated");
    }
    public static UserException createPasswordNull() {
        return new UserException("register.password.null");
    }
    public static UserException createNameNull() {
        return new UserException("register.name.null");
    }
    //Login
    public static UserException loginFailEmailNotFound() {
        return new UserException("login.fail");
    }
    public static UserException loginFailPasswordIncorrect() {
        return new UserException("login.fail");
    }
}
