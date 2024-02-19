package com.example.exception;

public class ChatErrorException extends RuntimeException {
    public ChatErrorException() {
        super("chat error");
    }
}