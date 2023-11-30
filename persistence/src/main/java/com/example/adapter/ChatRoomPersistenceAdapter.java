package com.example.adapter;

import com.example.data.ChatRoomMapper;
import com.example.model.ChatRoom;
import com.example.port.ExistsChatRoomPort;
import com.example.port.SaveChatRoomPort;
import com.example.repository.ChatRoomRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class ChatRoomPersistenceAdapter implements ExistsChatRoomPort, SaveChatRoomPort {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    public ChatRoomPersistenceAdapter(ChatRoomRepository chatRoomRepository, ChatRoomMapper chatRoomMapper) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomMapper = chatRoomMapper;
    }

    @Override
    public boolean existsChatRoom(String name) {
        return chatRoomRepository.findByOwner(name).isPresent();
    }

    @Override
    public Optional<ChatRoom> load(String name) {
        return chatRoomRepository.findByOwner(name).map(chatRoomMapper::entityToModel);
    }

    @Override
    public void save(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoomMapper.modelToEntity(chatRoom));
    }
}