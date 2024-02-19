package com.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserChatRoom {
    private final Long id;
    private final User user;
    private final ChatRoom chatRoom;

    public static UserChatRoom of(Long id, User user, ChatRoom chatRoom) {
        return new UserChatRoom(id, user, chatRoom);
    }
}
