package com.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ChatRoom {
    private final Long id;
    private final String title;
    private final Long owner;

    public static ChatRoom of(Long id, String title, Long owner) {
        return new ChatRoom(id, title, owner);
    }
}