package com.example.data.impl;

import com.example.data.ChatRoomMapper;
import com.example.entity.ChatRoomJPAEntity;
import com.example.model.ChatRoom;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapperImpl implements ChatRoomMapper {
    @Override
    public ChatRoomJPAEntity modelToEntity(ChatRoom chatRoom) {
        ChatRoomJPAEntity entity = new ChatRoomJPAEntity();
        entity.setTitle(chatRoom.getTitle());
        entity.setOwner(chatRoom.getOwner());
        return entity;
    }

    @Override
    public ChatRoom entityToModel(ChatRoomJPAEntity entity) {
        return ChatRoom.of(entity.getTitle(), entity.getOwner());
    }
}