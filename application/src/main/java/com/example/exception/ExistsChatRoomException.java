package com.example.exception;

public class ExistsChatRoomException extends RuntimeException {
    public ExistsChatRoomException() {
        super("There is a chatroom that already exists.");
    }
}