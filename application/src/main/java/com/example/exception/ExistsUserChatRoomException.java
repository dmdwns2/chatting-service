package com.example.exception;

public class ExistsUserChatRoomException extends RuntimeException{
    public ExistsUserChatRoomException() {
        super("It's a chat room that's already in.");
    }
}
