package com.example.data.impl;

import com.example.data.UserChatRoomMapper;
import com.example.entity.UserChatRoomJPAEntity;
import com.example.model.UserChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserChatRoomMapperImpl implements UserChatRoomMapper {
    private final UserMapperImpl userMapper;
    private final ChatRoomMapperImpl chatRoomMapper;

    @Override
    public UserChatRoomJPAEntity modelToEntity(final UserChatRoom userChatRoom) {
        UserChatRoomJPAEntity entity = new UserChatRoomJPAEntity();
        entity.setId(userChatRoom.getId());
        entity.setUser(userMapper.modelToEntity(userChatRoom.getUser()));
        entity.setChatroom(chatRoomMapper.modelToEntity(userChatRoom.getChatRoom()));
        return entity;
    }

    @Override
    public UserChatRoom entityToModel(final UserChatRoomJPAEntity userChatroomJPAEntity) {
        return UserChatRoom.of(userChatroomJPAEntity.getId(),
                userMapper.entityToModel(userChatroomJPAEntity.getUser()),
                chatRoomMapper.entityToModel(userChatroomJPAEntity.getChatroom()));
    }
}
