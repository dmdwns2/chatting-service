package com.example.usecase.impl;

import com.example.dto.ChatRoomCreateCommand;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.dto.ChatRoomDto;
import com.example.exception.ExistsChatRoomException;
import com.example.model.ChatRoom;
import com.example.port.CurrentDataTimePort;
import com.example.port.ExistsChatRoomPort;
import com.example.port.LoadChatRoomPort;
import com.example.port.SaveChatRoomPort;
import com.example.stereotype.UseCase;
import com.example.usecase.ChatRoomUseCase;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
public class ChatRoomUseCaseImpl implements ChatRoomUseCase {
    private final ExistsChatRoomPort existsChatRoomPort;
    private final SaveChatRoomPort saveChatRoomPort;
    private final LoadChatRoomPort loadChatRoomPort;
    private final CurrentDataTimePort currentDataTimePort;

    public ChatRoomUseCaseImpl(ExistsChatRoomPort existsChatRoomPort, SaveChatRoomPort saveChatRoomPort
            , LoadChatRoomPort loadChatRoomPort, CurrentDataTimePort currentDataTimePort) {
        this.existsChatRoomPort = existsChatRoomPort;
        this.saveChatRoomPort = saveChatRoomPort;
        this.loadChatRoomPort = loadChatRoomPort;
        this.currentDataTimePort = currentDataTimePort;
    }

    @Transactional
    @Override
    public ChatRoomCreatedEvent create(ChatRoomCreateCommand command, String name) {
        if (existsChatRoomPort.existsChatRoom(name)) {
            throw new ExistsChatRoomException();
        }
        ChatRoom chatRoom = ChatRoom.of(command.getTitle(), name);

        saveChatRoomPort.save(chatRoom);
        return new ChatRoomCreatedEvent(chatRoom.getOwner(), chatRoom.getTitle(), currentDataTimePort.now());
    }

    @Override
    public List<ChatRoomDto> getList(Pageable pageable) {
        Page<ChatRoom> pages = loadChatRoomPort.findAll(pageable);
        return getChatRoomDto(pages);
    }

    private static List<ChatRoomDto> getChatRoomDto(Page<ChatRoom> pages) {
        return pages.stream()
                .map(ChatRoomDto::of)
                .collect(Collectors.toList());
    }
}