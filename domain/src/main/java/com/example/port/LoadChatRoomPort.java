package com.example.port;

import com.example.model.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadChatRoomPort {
    Optional<ChatRoom> load(String name);

    Page<ChatRoom> findAll(Pageable pageable);
}
