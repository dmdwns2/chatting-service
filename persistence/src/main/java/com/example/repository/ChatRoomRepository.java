package com.example.repository;

import com.example.entity.ChatRoomJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomJPAEntity, Long> {
    Optional<ChatRoomJPAEntity> findChatRoomById(Long roomId);
    Optional<ChatRoomJPAEntity> findChatRoomByOwner(Long owner);

    void deleteChatRoomById(Long roomId);
}