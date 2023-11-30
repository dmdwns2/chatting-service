package com.example.data;

import com.example.entity.ChatRoomJPAEntity;
import com.example.model.ChatRoom;

public interface ChatRoomMapper {
    ChatRoomJPAEntity modelToEntity(ChatRoom chatRoom);

    ChatRoom entityToModel(ChatRoomJPAEntity entity);
}