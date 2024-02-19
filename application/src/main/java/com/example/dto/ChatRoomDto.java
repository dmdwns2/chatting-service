package com.example.dto;

import com.example.model.ChatRoom;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatRoomDto {
    private String title;
    private Long owner;

    public static ChatRoomDto of(ChatRoom chatRoom) {
        return new ChatRoomDto(chatRoom.getTitle(), chatRoom.getOwner());
    }
}
