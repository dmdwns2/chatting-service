package com.example.service;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.dto.ChatRoomDto;
import com.example.exception.ExistsChatRoomException;
import com.example.exception.NotExistsChatRoomException;
import com.example.exception.NotFoundUserException;
import com.example.model.ChatRoom;
import com.example.model.User;
import com.example.model.UserChatRoom;
import com.example.port.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceServiceImpl implements ChatRoomService {
    private final ExistsChatRoomPort existsChatRoomPort;
    private final SaveChatRoomPort saveChatRoomPort;
    private final LoadChatRoomPort loadChatRoomPort;
    private final CurrentDataTimePort currentDataTimePort;
    private final LoadUserPort loadUserPort;
    private final SaveUserChatRoomPort saveUserChatRoomPort;
    private final DeleteUserChatRoomPort deleteUserChatRoomPort;
    private final DeleteChatRoomPort deleteChatRoomPort;

    @Transactional
    @Override
    public ChatRoomCreatedEvent create(ChatRoomCreateRequest chatRoomCreateRequest, Long userId) {
        if (existsChatRoomPort.existsChatRoomByOwner(userId)) {
            throw new ExistsChatRoomException();
        }
        User user = loadUserPort.load(userId).orElseThrow(() -> new NotFoundUserException(userId.toString()));
        ChatRoom chatRoom = ChatRoom.builder()
                .owner(user.getId())
                .title(chatRoomCreateRequest.getTitle())
                .build();

        saveChatRoomPort.save(chatRoom);
        return new ChatRoomCreatedEvent(chatRoom.getOwner(), chatRoom.getTitle(), currentDataTimePort.now());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ChatRoomDto> getList(Pageable pageable) {
        Page<ChatRoom> pages = loadChatRoomPort.findAll(pageable);
        return getChatRoomDto(pages);
    }

    @Transactional
    @Override
    public void join(Long roomId, Long userId) {
        if (!existsChatRoomPort.existsChatRoomById(roomId)) {
            throw new NotExistsChatRoomException();
        }
        ChatRoom chatRoom = loadChatRoomPort.load(roomId)
                .orElseThrow(NotExistsChatRoomException::new);
        User user = loadUserPort.load(userId).orElseThrow(() -> new NotFoundUserException(userId.toString()));
        UserChatRoom userChatRoom = UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
        saveUserChatRoomPort.save(userChatRoom);
    }

    @Override
    @Transactional
    public void exit(Long userId, Long roomId) {
        ChatRoom chatRoom = loadChatRoomPort.load(roomId)
                .orElseThrow(NotExistsChatRoomException::new);

        if (Objects.equals(chatRoom.getOwner(), userId)) {
            deleteUserChatRoomPort.delete(userId);
            deleteChatRoomPort.delete(userId);
        }
        if (!Objects.equals(chatRoom.getId(), userId)) {
            deleteUserChatRoomPort.delete(userId);
        }
    }

    @Override
    @Transactional
    public Long findUserIdByName(String name) {
        return loadUserPort.load(name).orElseThrow(() -> new NotFoundUserException(name)).getId();
    }

    private static List<ChatRoomDto> getChatRoomDto(Page<ChatRoom> pages) {
        return pages.stream()
                .map(ChatRoomDto::of)
                .collect(Collectors.toList());
    }

}