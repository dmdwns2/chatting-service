package com.example.exception;

public class DuplicateNicknameException extends RuntimeException{
    public DuplicateNicknameException(String nickname){
        super("[" + nickname + "] is duplicated");
    }
}