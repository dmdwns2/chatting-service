package com.example.exception;

public class NotExistsChatMsgException extends RuntimeException {
    public NotExistsChatMsgException() {
        super("No such chat message exists.");
    }
}