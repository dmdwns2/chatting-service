package com.example.port;

public interface DeleteUserChatRoomByUserIdPort {
    void deleteByUserIdAndChatRoomId(Long userId, Long roomId);
}
