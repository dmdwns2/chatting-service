package com.example.exception;

public class NotExistsChatRoomException extends RuntimeException {
    public NotExistsChatRoomException() {
        super("No such chat room exists.");
    }
}