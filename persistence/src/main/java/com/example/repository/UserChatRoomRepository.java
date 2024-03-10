package com.example.repository;

import com.example.entity.UserChatRoomJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoomJPAEntity, Long> {
    // chatroom id, user id가 and 인 user_chatroom 데이터 삭제
    @Modifying
    @Query("DELETE FROM user_chatroom uc WHERE uc.user.id = :user_id AND uc.chatroom.id = :chatroom_id")
    void deleteUserChatRoomByUserIdAndChatRoomId(@Param("user_id") Long userId, @Param("chatroom_id") Long roomId);

    int countByChatroomId(Long roomId);

    boolean existsByUserIdAndChatroomId(Long userId, Long roomId);

    // chatroom id를 기준으로 특정 user의 name 목록을 추출
    @Query("SELECT uc.user.name FROM user_chatroom uc WHERE uc.chatroom.id = :chatroom_id")
    List<String> findUserNamesByChatRoomId(@Param("chatroom_id") Long roomId);
}
