package com.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ChatMsg {
    private final Long id;
    private final String message;
    private final User user;
    private final ChatRoom chatroom;
    private final LocalDateTime sendTime;

    public static ChatMsg of(Long id, String message, User user, ChatRoom chatroom, LocalDateTime sendTime) {
        return new ChatMsg(id, message, user, chatroom, sendTime);
    }
}