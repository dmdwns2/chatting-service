package com.example;

import com.example.entity.ChatMsgJPAEntity;

import java.util.List;

public interface LoadChatMsgCustomPort {
    List<ChatMsgJPAEntity> findOwnerByChatMsg(Long chatMsg, Long lastId);
}
