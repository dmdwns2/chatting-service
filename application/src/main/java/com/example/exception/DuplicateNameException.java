package com.example.exception;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String name) {
        super("[" + name + "] is duplicated");
    }
}