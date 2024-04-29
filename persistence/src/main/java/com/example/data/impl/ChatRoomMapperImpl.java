package com.example.data.impl;

import com.example.data.ChatRoomMapper;
import com.example.entity.ChatRoomJPAEntity;
import com.example.model.ChatRoom;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapperImpl implements ChatRoomMapper {
    @Override
    public ChatRoomJPAEntity modelToEntity(final ChatRoom chatRoom) {
        ChatRoomJPAEntity entity = new ChatRoomJPAEntity();
        entity.setId(chatRoom.getId());
        entity.setTitle(chatRoom.getTitle());
        entity.setOwner(chatRoom.getOwner());
        return entity;
    }

    @Override
    public ChatRoom entityToModel(final ChatRoomJPAEntity entity) {
        return ChatRoom.of(entity.getId(), entity.getTitle(), entity.getOwner());
    }
}