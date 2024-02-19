package com.example.port;

import com.example.model.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadChatRoomPort {
    Optional<ChatRoom> load(Long roomId);
    Optional<ChatRoom> loadByOwner(Long owner);

    Page<ChatRoom> findAll(Pageable pageable);
}
