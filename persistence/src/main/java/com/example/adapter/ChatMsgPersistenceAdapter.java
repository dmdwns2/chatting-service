package com.example.adapter;

import com.example.data.ChatMsgMapper;
import com.example.model.ChatMsg;
import com.example.port.DeleteChatMsgPort;
import com.example.port.SaveChatMsgPort;
import com.example.repository.ChatMsgRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChatMsgPersistenceAdapter implements SaveChatMsgPort, DeleteChatMsgPort {
    private final ChatMsgRepository chatMsgRepository;
    private final ChatMsgMapper chatMsgMapper;

    @Override
    public void delete(Long chatRoomId) {
        chatMsgRepository.deleteChatMsgByChatroom_Id(chatRoomId);
    }

    @Override
    public void save(ChatMsg chatMsg) {
        chatMsgRepository.save(chatMsgMapper.modelToEntity(chatMsg));
    }
}
