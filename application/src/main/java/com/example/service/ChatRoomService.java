package com.example.service;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.dto.ChatRoomDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomService {
    ChatRoomCreatedEvent create(ChatRoomCreateRequest chatRoomCreateRequest, Long userId);

    List<ChatRoomDto> getList(Pageable pageable);

    void join(Long owner, Long userId);

    void exit(Long owner, Long userId);
}