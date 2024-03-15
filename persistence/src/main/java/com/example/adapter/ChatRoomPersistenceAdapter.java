package com.example.adapter;

import com.example.data.ChatRoomMapper;
import com.example.model.ChatRoom;
import com.example.port.DeleteChatRoomPort;
import com.example.port.ExistsChatRoomPort;
import com.example.port.LoadChatRoomPort;
import com.example.port.SaveChatRoomPort;
import com.example.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatRoomPersistenceAdapter implements ExistsChatRoomPort, SaveChatRoomPort, LoadChatRoomPort, DeleteChatRoomPort {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    public boolean existsChatRoomById(Long roomId) {
        return chatRoomRepository.findChatRoomById(roomId).isPresent();
    }

    @Override
    public boolean existsChatRoomByOwner(Long owner) {
        return chatRoomRepository.findChatRoomByOwner(owner).isPresent();
    }

    @Override
    public Optional<ChatRoom> loadById(Long chatRoomId) {
        return chatRoomRepository.findChatRoomById(chatRoomId).map(chatRoomMapper::entityToModel);
    }

    @Override
    public Optional<ChatRoom> loadByOwner(Long owner) {
        return chatRoomRepository.findChatRoomByOwner(owner).map(chatRoomMapper::entityToModel);
    }

    @Override
    public Page<ChatRoom> loadChatRoomPage(Pageable pageable) {
        return chatRoomRepository.findAll(pageable).map(chatRoomMapper::entityToModel);
    }

    @Override
    public void save(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoomMapper.modelToEntity(chatRoom));
    }

    @Override
    public void deleteById(Long roomId) {
        chatRoomRepository.deleteChatRoomById(roomId);
    }
}