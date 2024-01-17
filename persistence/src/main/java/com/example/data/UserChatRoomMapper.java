package com.example.data;

import com.example.entity.UserChatRoomJPAEntity;
import com.example.model.UserChatRoom;

public interface UserChatRoomMapper {
    UserChatRoom entityToModel(UserChatRoomJPAEntity userChatroomJPAEntity);

    UserChatRoomJPAEntity modelToEntity(UserChatRoom userChatRoom);
}
