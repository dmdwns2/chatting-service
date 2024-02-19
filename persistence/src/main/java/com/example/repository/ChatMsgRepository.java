package com.example.repository;

import com.example.entity.ChatMsgJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMsgRepository extends JpaRepository<ChatMsgJPAEntity, Long> {
    void deleteChatMsgByChatRoom_Id(Long chatRoomId);
}
