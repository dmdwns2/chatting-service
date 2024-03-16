package com.example;

import com.example.model.ChatMsg;

import java.util.List;

public interface LoadChatMsgCustomPort {
    List<ChatMsg> findChatroomIdByChatMsg(Long chatMsg, Long lastId);
}
