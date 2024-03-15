package com.example.port;

import java.util.List;

public interface LoadUserListOfChatRoomPort {
    List<String> loadUserNamesByChatRoomId(Long roomId);
}
