package com.example.exception;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException(String nickname) {
        super("[" + nickname + "] could not be found");
    }
}