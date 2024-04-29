package com.example.data.impl;

import com.example.data.ChatMsgMapper;
import com.example.data.ChatRoomMapper;
import com.example.data.UserMapper;
import com.example.entity.ChatMsgJPAEntity;
import com.example.model.ChatMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMsgMapperImpl implements ChatMsgMapper {
    private final ChatRoomMapper chatRoomMapper;
    private final UserMapper userMapper;

    @Override
    public ChatMsgJPAEntity modelToEntity(final ChatMsg chatMsg) {
        ChatMsgJPAEntity entity = new ChatMsgJPAEntity();
        entity.setId(chatMsg.getId());
        entity.setMessage(chatMsg.getMessage());
        entity.setUser(userMapper.modelToEntity(chatMsg.getUser()));
        entity.setChatroom(chatRoomMapper.modelToEntity(chatMsg.getChatroom()));
        entity.setSendTime(chatMsg.getSendTime());
        return entity;
    }

    @Override
    public ChatMsg entityToModel(final ChatMsgJPAEntity entity) {
        return ChatMsg.of(entity.getId(), entity.getMessage(), userMapper.entityToModel(entity.getUser()),
                chatRoomMapper.entityToModel(entity.getChatroom()), entity.getSendTime());
    }
}
