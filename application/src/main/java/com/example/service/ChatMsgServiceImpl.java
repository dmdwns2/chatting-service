package com.example.service;

import com.example.LoadChatMsgCustomPort;
import com.example.dto.ChatMsgDto;
import com.example.dto.ChatMsgRequest;
import com.example.dto.ChatMsgResponse;
import com.example.entity.ChatMsgJPAEntity;
import com.example.exception.NotExistsChatRoomException;
import com.example.exception.NotFoundUserException;
import com.example.model.ChatMsg;
import com.example.model.ChatRoom;
import com.example.model.User;
import com.example.port.LoadChatRoomPort;
import com.example.port.LoadUserPort;
import com.example.port.SaveChatMsgPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatMsgServiceImpl implements ChatMsgService {
    private final SaveChatMsgPort saveChatMsgPort;
    private final LoadUserPort loadUserPort;
    private final LoadChatRoomPort loadChatRoomPort;
    private final LoadChatMsgCustomPort loadChatMsgCustomPort;

    @Override
    public ChatMsgResponse sendMessage(ChatMsgRequest message, Long userId, Long roomId) {
        User user = loadUserPort.loadById(userId)
                .orElseThrow(() -> new NotFoundUserException(userId.toString()));
        ChatRoom chatRoom = loadChatRoomPort.loadById(roomId)
                .orElseThrow(NotExistsChatRoomException::new);

        ChatMsg chatMsg = ChatMsg.builder()
                .message(message.getMessage())
                .user(user)
                .chatroom(chatRoom)
                .build();
        ChatMsgResponse response = ChatMsgResponse.builder()
                .owner(chatRoom.getOwner())
                .from(user.getNickname())
                .message(message.getMessage())
                .build();

        saveEntryChatMsg(chatMsg);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMsgDto> getChatMsgList(Long roomId, Long userId, Long lastId) {
        List<ChatMsgJPAEntity> chatMsgsList = loadChatMsgCustomPort.findChatroomIdByChatMsg(roomId, lastId);
        List<ChatMsgDto> chatMsgDtos = new ArrayList<>();

        for (ChatMsgJPAEntity chatMsg : chatMsgsList) {
            ChatMsgDto build = ChatMsgDto.builder()
                    .chatMsgId(chatMsg.getId())
                    .nickname(chatMsg.getUser().getNickname())
                    .message(chatMsg.getMessage())
                    .userId(chatMsg.getUser().getId())
                    .build();
            if (Objects.isNull(lastId) || chatMsg.getId() > lastId) {
                chatMsgDtos.add(build); // 에러 의심 구간
            }
        }
        return chatMsgDtos;
    }

    @Transactional
    private void saveEntryChatMsg(ChatMsg chatMsg) {
        saveChatMsgPort.save(chatMsg);
    }

    @Override
    @Transactional(readOnly = true)
    public Long findUserIdByName(String name) {
        return loadUserPort.loadByName(name).orElseThrow(() -> new NotFoundUserException(name)).getId();
    }
}
