package com.example.usecase.impl;

import com.example.dto.ChatRoomCreateCommand;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.exception.ExistsChatRoomException;
import com.example.model.ChatRoom;
import com.example.port.CurrentDataTimePort;
import com.example.port.ExistsChatRoomPort;
import com.example.port.SaveChatRoomPort;
import com.example.stereotype.UseCase;
import com.example.usecase.ChatRoomUseCase;
import jakarta.transaction.Transactional;

@UseCase
public class ChatRoomUseCaseImpl implements ChatRoomUseCase {
    private final ExistsChatRoomPort existsChatRoomPort;
    private final SaveChatRoomPort saveChatRoomPort;
    private final CurrentDataTimePort currentDataTimePort;

    public ChatRoomUseCaseImpl(ExistsChatRoomPort existsChatRoomPort, SaveChatRoomPort saveChatRoomPort, CurrentDataTimePort currentDataTimePort) {
        this.existsChatRoomPort = existsChatRoomPort;
        this.saveChatRoomPort = saveChatRoomPort;
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
}