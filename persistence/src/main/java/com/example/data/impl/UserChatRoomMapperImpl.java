package com.example.data.impl;

import com.example.data.UserChatRoomMapper;
import com.example.entity.UserChatRoomJPAEntity;
import com.example.model.UserChatRoom;
import org.springframework.stereotype.Component;

@Component
public class UserChatRoomMapperImpl implements UserChatRoomMapper {
    private final UserMapperImpl userMapper;
    private final ChatRoomMapperImpl chatRoomMapper;

    public UserChatRoomMapperImpl(UserMapperImpl userMapper, ChatRoomMapperImpl chatRoomMapper) {
        this.userMapper = userMapper;
        this.chatRoomMapper = chatRoomMapper;
    }

    @Override
    public UserChatRoomJPAEntity modelToEntity(UserChatRoom userChatRoom) {
        UserChatRoomJPAEntity entity = new UserChatRoomJPAEntity();
        entity.setUser(userMapper.modelToEntity(userChatRoom.getUser()));
        entity.setChatroom(chatRoomMapper.modelToEntity(userChatRoom.getChatRoom()));
        return entity;
    }

    @Override
    public UserChatRoom entityToModel(UserChatRoomJPAEntity userChatroomJPAEntity) {
        return UserChatRoom.of(userMapper.entityToModel(userChatroomJPAEntity.getUser()),
                chatRoomMapper.entityToModel(userChatroomJPAEntity.getChatroom()));
    }
}
