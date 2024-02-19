package com.example.port;

public interface ExistsChatRoomPort {
    boolean existsChatRoomById(Long roomId);

    boolean existsChatRoomByOwner(Long owner);
}