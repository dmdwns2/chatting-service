package com.example;

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
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomServiceServiceImpl implements ChatRoomService {
    private final ExistsChatRoomPort existsChatRoomPort;
    private final SaveChatRoomPort saveChatRoomPort;
    private final LoadChatRoomPort loadChatRoomPort;
    private final CurrentDataTimePort currentDataTimePort;
    private final LoadUserPort loadUserPort;
    private final SaveUserChatRoomPort saveUserChatRoomPort;

    public ChatRoomServiceServiceImpl(ExistsChatRoomPort existsChatRoomPort, SaveChatRoomPort saveChatRoomPort
            , LoadChatRoomPort loadChatRoomPort, CurrentDataTimePort currentDataTimePort, LoadUserPort loadUserPort
            , SaveUserChatRoomPort saveUserChatRoomPort) {
        this.existsChatRoomPort = existsChatRoomPort;
        this.saveChatRoomPort = saveChatRoomPort;
        this.loadChatRoomPort = loadChatRoomPort;
        this.currentDataTimePort = currentDataTimePort;
        this.loadUserPort = loadUserPort;
        this.saveUserChatRoomPort = saveUserChatRoomPort;
    }

    @Transactional
    @Override
    public ChatRoomCreatedEvent create(ChatRoomCreateRequest chatRoomCreateRequest, String name) {
        if (existsChatRoomPort.existsChatRoom(name)) {
            throw new ExistsChatRoomException();
        }
        com.example.model.ChatRoom chatRoom = com.example.model.ChatRoom.of(chatRoomCreateRequest.getTitle(), name);

        saveChatRoomPort.save(chatRoom);
        return new ChatRoomCreatedEvent(chatRoom.getOwner(), chatRoom.getTitle(), currentDataTimePort.now());
    }

    @Override
    public List<ChatRoomDto> getList(Pageable pageable) {
        Page<com.example.model.ChatRoom> pages = loadChatRoomPort.findAll(pageable);
        return getChatRoomDto(pages);
    }

    @Transactional
    @Override
    public void join(String owner, String name) {
        if (!existsChatRoomPort.existsChatRoom(owner)) {
            throw new NotExistsChatRoomException();
        }
        ChatRoom chatRoom = loadChatRoomPort.load(owner)
                .orElseThrow(NotExistsChatRoomException::new);
        User user = loadUserPort.load(name).orElseThrow(() -> new NotFoundUserException(name));
        UserChatRoom userChatRoom = UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
        saveUserChatRoomPort.save(userChatRoom);
    }

    private static List<ChatRoomDto> getChatRoomDto(Page<com.example.model.ChatRoom> pages) {
        return pages.stream()
                .map(ChatRoomDto::of)
                .collect(Collectors.toList());
    }
}