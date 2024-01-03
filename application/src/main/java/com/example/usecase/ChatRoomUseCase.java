package com.example.usecase;

import com.example.dto.ChatRoomCreateCommand;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.dto.ChatRoomDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomUseCase {
    ChatRoomCreatedEvent create(ChatRoomCreateCommand command, String name);

    List<ChatRoomDto> getList(Pageable pageable);
}