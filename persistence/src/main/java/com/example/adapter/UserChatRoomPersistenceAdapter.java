package com.example.adapter;

import com.example.data.UserChatRoomMapper;
import com.example.model.UserChatRoom;
import com.example.port.*;
import com.example.repository.UserChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserChatRoomPersistenceAdapter implements SaveUserChatRoomPort, DeleteUserChatRoomByUserIdPort
        , LoadNumOfUserByChatRoomPort, ExistsUserChatRoomPort, LoadUserListOfChatRoomPort {
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserChatRoomMapper userChatRoomMapper;

    @Override
    public void save(final UserChatRoom userChatRoom) {
        userChatRoomRepository.save(userChatRoomMapper.modelToEntity(userChatRoom));
    }

    @Override
    public void deleteByUserIdAndChatRoomId(final Long userId, final Long roomId) {
        userChatRoomRepository.deleteUserChatRoomByUserIdAndChatRoomId(userId, roomId);
    }

    @Override
    public boolean existsByUserIdAndChatRoomId(final Long userId, final Long roomId) {
        return userChatRoomRepository.existsByUserIdAndChatroomId(userId, roomId);
    }

    @Override
    public int loadNumOfUsersByChatRoom(final Long roomId) {
        return userChatRoomRepository.countByChatroomId(roomId);
    }

    @Override
    public List<String> loadUserNamesByChatRoomId(final Long roomId) {
        return userChatRoomRepository.findUserNamesByChatRoomId(roomId);
    }
}
