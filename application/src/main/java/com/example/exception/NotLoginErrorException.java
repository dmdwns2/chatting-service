package com.example.exception;

public class NotLoginErrorException extends RuntimeException {
    public NotLoginErrorException() {
        super("you are not logged in");
    }
}
