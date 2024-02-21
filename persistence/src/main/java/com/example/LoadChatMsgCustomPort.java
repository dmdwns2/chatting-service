package com.example;

import com.example.entity.ChatMsgJPAEntity;

import java.util.List;

public interface LoadChatMsgCustomPort {
    List<ChatMsgJPAEntity> findChatroomIdByChatMsg(Long chatMsg, Long lastId);
}
