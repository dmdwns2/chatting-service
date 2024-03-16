package com.example.exception;

public class NotExistsUserInChatRoom extends RuntimeException{
    public NotExistsUserInChatRoom() {
        super("This user does not exist in this chatroom.");
    }
}
