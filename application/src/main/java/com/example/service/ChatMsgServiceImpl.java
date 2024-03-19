package com.example.service;

import com.example.LoadChatMsgCustomPort;
import com.example.dto.ChatMsgDto;
import com.example.dto.ChatMsgRequest;
import com.example.dto.ChatMsgResponse;
import com.example.exception.NotExistsChatRoomException;
import com.example.exception.NotExistsUserInChatRoom;
import com.example.exception.NotFoundUserException;
import com.example.model.ChatMsg;
import com.example.model.ChatRoom;
import com.example.model.User;
import com.example.port.*;
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
    private final ExistsUserChatRoomPort existsUserChatRoomPort;
    private final CurrentDataTimePort currentDataTimePort;

    @Override
    public ChatMsgResponse sendMessage(ChatMsgRequest message, Long userId, Long roomId) {
        if(!existsUserChatRoomPort.existsByUserIdAndChatRoomId(userId, roomId)){
            throw new NotExistsUserInChatRoom();
        }
        final User user = loadUserPort.loadById(userId)
                .orElseThrow(() -> new NotFoundUserException(userId.toString()));
        final ChatRoom chatRoom = loadChatRoomPort.loadById(roomId)
                .orElseThrow(NotExistsChatRoomException::new);

        ChatMsg chatMsg = ChatMsg.builder()
                .message(message.getMessage())
                .user(user)
                .chatroom(chatRoom)
                .sendTime(currentDataTimePort.now())
                .build();
        ChatMsgResponse response = ChatMsgResponse.builder()
                .owner(chatRoom.getOwner())
                .from(user.getNickname())
                .message(message.getMessage())
                .sendTime(chatMsg.getSendTime())
                .build();

        saveChatMsgPort.save(chatMsg);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMsgDto> getChatMsgList(Long roomId, Long userId, Long lastId) {
        if(!existsUserChatRoomPort.existsByUserIdAndChatRoomId(userId, roomId)){
            throw new NotExistsUserInChatRoom();
        }
        final List<ChatMsg> chatMsgsList = loadChatMsgCustomPort.findChatroomIdByChatMsg(roomId, lastId);
        List<ChatMsgDto> chatMsgDtos = new ArrayList<>();

        for (ChatMsg chatMsg : chatMsgsList) {
            ChatMsgDto build = ChatMsgDto.builder()
                    .chatMsgId(chatMsg.getId())
                    .nickname(chatMsg.getUser().getNickname())
                    .message(chatMsg.getMessage())
                    .userId(chatMsg.getUser().getId())
                    .sendTime(chatMsg.getSendTime())
                    .build();
            if (Objects.isNull(lastId) || chatMsg.getId() > lastId) {
                chatMsgDtos.add(build);
            }
        }
        return chatMsgDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Long findUserIdByName(String name) {
        return loadUserPort.loadByName(name).orElseThrow(() -> new NotFoundUserException(name)).getId();
    }
}
