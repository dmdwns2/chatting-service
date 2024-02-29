package com.example.port;

public interface ExsistUserChatRoomPort {
    boolean exsistByUserIdAndChatroomId(Long userId, Long roomId);
}
