package com.example;

import com.example.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomService {
    ChatRoomCreatedEvent create(ChatRoomCreateRequest chatRoomCreateReques, String name);

    List<ChatRoomDto> getList(Pageable pageable);

    void join(String owner , String name);
}