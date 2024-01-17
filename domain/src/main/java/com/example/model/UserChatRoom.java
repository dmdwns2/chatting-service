package com.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserChatRoom {
    private final User user;
    private final ChatRoom chatRoom;

    public static UserChatRoom of(User user, ChatRoom chatRoom) {
        return new UserChatRoom(user, chatRoom);
    }
}
