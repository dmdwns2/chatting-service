package com.example.service;

import com.example.dto.ChatMsgDto;
import com.example.dto.ChatMsgRequest;
import com.example.dto.ChatMsgResponse;

import java.util.List;

public interface ChatMsgService {
    ChatMsgResponse sendMessage(ChatMsgRequest message, Long userId, Long roomId);

    List<ChatMsgDto> getChatMsgList(Long roomId, Long userId, Long lastId);
}
