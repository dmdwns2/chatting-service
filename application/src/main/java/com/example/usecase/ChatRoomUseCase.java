package com.example.usecase;

import com.example.dto.ChatRoomCreateCommand;
import com.example.dto.ChatRoomCreatedEvent;

public interface ChatRoomUseCase {
    ChatRoomCreatedEvent create(ChatRoomCreateCommand command, String name);
}