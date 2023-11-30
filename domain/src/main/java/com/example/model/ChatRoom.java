package com.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChatRoom {
    private final String title;
    private final String owner;

    public static ChatRoom of(String title, String owner) {
        return new ChatRoom(title, owner);
    }
}