package com.example.exception;

public class NotMatchPasswordException extends RuntimeException{
    public NotMatchPasswordException() {
        super("Password does not match");
    }
}