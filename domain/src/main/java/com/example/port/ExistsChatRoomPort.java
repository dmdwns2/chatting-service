package com.example.port;

import com.example.model.ChatRoom;

import java.util.Optional;

public interface ExistsChatRoomPort {
    boolean existsChatRoom(String name);

    Optional<ChatRoom> load(String name);
}