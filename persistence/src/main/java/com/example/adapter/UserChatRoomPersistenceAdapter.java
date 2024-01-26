package com.example.adapter;

import com.example.data.UserChatRoomMapper;
import com.example.model.UserChatRoom;
import com.example.port.DeleteUserChatRoomPort;
import com.example.port.SaveUserChatRoomPort;
import com.example.repository.UserChatRoomRepository;
import org.springframework.stereotype.Component;

@Component
public class UserChatRoomPersistenceAdapter implements SaveUserChatRoomPort, DeleteUserChatRoomPort {
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserChatRoomMapper userChatRoomMapper;

    public UserChatRoomPersistenceAdapter(UserChatRoomRepository userChatRoomRepository
            , UserChatRoomMapper userChatRoomMapper) {
        this.userChatRoomRepository = userChatRoomRepository;
        this.userChatRoomMapper = userChatRoomMapper;
    }

    @Override
    public void save(UserChatRoom userChatRoom) {
        userChatRoomRepository.save(userChatRoomMapper.modelToEntity(userChatRoom));
    }

    @Override
    public void delete(String name) {
        userChatRoomRepository.deleteUserChatRoomByUser_Name(name);
    }
}
