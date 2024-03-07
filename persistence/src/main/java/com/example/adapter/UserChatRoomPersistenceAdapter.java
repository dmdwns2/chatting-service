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
public class UserChatRoomPersistenceAdapter implements SaveUserChatRoomPort, DeleteUserChatRoomPort
        , LoadNumOfUserByChatRoomPort, ExsistUserChatRoomPort, LoadUserListOfChatRoomPort {
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserChatRoomMapper userChatRoomMapper;

    @Override
    public void save(UserChatRoom userChatRoom) {
        userChatRoomRepository.save(userChatRoomMapper.modelToEntity(userChatRoom));
    }

    @Override
    public void delete(Long id) {
        userChatRoomRepository.deleteUserChatRoomById(id);
    }

    @Override
    public boolean exsistByUserIdAndChatroomId(Long userId, Long roomId) {
        return userChatRoomRepository.existsByUserIdAndChatroomId(userId, roomId);
    }

    @Override
    public int loadNumOfUserByChatRoom(Long roomId) {
        return userChatRoomRepository.countByChatroomId(roomId);
    }

    @Override
    public List<String> findUserNamesByChatRoomId(Long roomId) {
        return userChatRoomRepository.findUserNamesByChatRoomId(roomId);
    }
}
