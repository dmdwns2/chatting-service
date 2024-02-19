package com.example.repository;

import com.example.entity.UserChatRoomJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoomJPAEntity, Long> {
    void deleteUserChatRoomById(Long Id);
}
