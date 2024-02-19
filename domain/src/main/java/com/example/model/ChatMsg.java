package com.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ChatMsg {
    private final Long id;
    private final String message;
    private final User user;
    private final ChatRoom chatroom;

    public static ChatMsg of(Long id, String message, User user, ChatRoom chatroom) {
        return new ChatMsg(id, message, user, chatroom);
    }
}