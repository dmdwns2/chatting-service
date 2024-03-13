package com.example.port;

public interface ExistsUserChatRoomPort {
    boolean existsByUserIdAndChatRoomId(Long userId, Long roomId);
}
