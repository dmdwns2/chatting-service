package com.example.exception;

public class NotExistsClientException extends RuntimeException {
    public NotExistsClientException() {
        super("No such client exists.");
    }
}