package com.example;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.dto.ChatRoomDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomService {
    ChatRoomCreatedEvent create(ChatRoomCreateRequest chatRoomCreateRequest, String name);

    List<ChatRoomDto> getList(Pageable pageable);

    void join(String owner, String name);

    void exit(String owner, String name);
}