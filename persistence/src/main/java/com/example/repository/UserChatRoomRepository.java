package com.example.repository;

import com.example.entity.UserChatRoomJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoomJPAEntity, Long> {
    void deleteUserChatRoomById(Long Id);

    int countByChatroomId(Long roomId);

    boolean existsByUserIdAndChatroomId(Long userId, Long roomId);

    // Chatroom id를 기준으로 특정 user의 name 목록을 추출
    @Query("SELECT uc.user.name FROM user_chatroom uc WHERE uc.chatroom.id = :chatroom_id")
    List<String> findUserNamesByChatRoomId(Long roomId);
}
