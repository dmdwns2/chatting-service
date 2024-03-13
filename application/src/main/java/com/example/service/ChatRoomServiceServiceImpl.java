package com.example.service;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.dto.ChatRoomDto;
import com.example.exception.ExistsChatRoomException;
import com.example.exception.NotExistsChatRoomException;
import com.example.exception.NotFoundUserException;
import com.example.model.ChatRoom;
import com.example.model.User;
import com.example.model.UserChatRoom;
import com.example.port.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceServiceImpl implements ChatRoomService {

    private final ExistsChatRoomPort existsChatRoomPort;
    private final SaveChatRoomPort saveChatRoomPort;
    private final LoadChatRoomPort loadChatRoomPort;
    private final CurrentDataTimePort currentDataTimePort;
    private final LoadUserPort loadUserPort;
    private final SaveUserChatRoomPort saveUserChatRoomPort;
    private final DeleteUserChatRoomByUserIdPort deleteUserChatRoomByUserIdPort;
    private final DeleteChatRoomPort deleteChatRoomPort;
    private final LoadNumOfUserByChatRoomPort loadNumOfUserByChatRoomPort;
    private final ExistsUserChatRoomPort existsUserChatRoomPort;
    private final LoadUserListOfChatRoomPort loadUserListOfChatRoomPort;

    @Override
    public ChatRoomCreatedEvent create(ChatRoomCreateRequest chatRoomCreateRequest, Long userId) {
        if (existsChatRoomPort.existsChatRoomByOwner(userId)) {
            throw new ExistsChatRoomException();
        }
        User user = loadUserPort.load(userId).orElseThrow(() -> new NotFoundUserException(userId.toString()));
        ChatRoom chatRoom = ChatRoom.builder()
                .owner(user.getId())
                .title(chatRoomCreateRequest.getTitle())
                .build();
        saveEntryChatRoom(chatRoom);
        return new ChatRoomCreatedEvent(chatRoom.getOwner(), chatRoom.getTitle(), currentDataTimePort.now());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ChatRoomDto> getList(Pageable pageable) {
        Page<ChatRoom> pages = loadChatRoomPort.findAll(pageable);
        return getChatRoomDto(pages);
    }

    @Override
    public void join(Long roomId, Long userId) {
        if (!existsChatRoomPort.existsChatRoomById(roomId)) {
            throw new NotExistsChatRoomException();
        }
        if (existsUserChatRoomPort.existsbyuseridandchatroomid(userId, roomId)) {
            throw new ExistsChatRoomException();
        }
        ChatRoom chatRoom = loadChatRoomPort.load(roomId)
                .orElseThrow(NotExistsChatRoomException::new);
        User user = loadUserPort.load(userId).orElseThrow(() -> new NotFoundUserException(userId.toString()));
        UserChatRoom userChatRoom = UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
        saveEntryUserChatRoom(userChatRoom);
    }

    @Override
    @Transactional
    public void leave(Long userId, Long roomId) {
        ChatRoom chatRoom = loadChatRoomPort.load(roomId)
                .orElseThrow(NotExistsChatRoomException::new);

        if (Objects.equals(chatRoom.getOwner(), userId)) {
            deleteUserChatRoomByUserIdPort.deleteByUserIdAndChatRoomId(userId, roomId);
            deleteChatRoomPort.deleteByChatRoomId(roomId);
        }
        if (!Objects.equals(chatRoom.getId(), userId)) {
            deleteUserChatRoomByUserIdPort.deleteByUserIdAndChatRoomId(userId, roomId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int loadNumOfUserByChatRoomId(Long roomId) {
        if (!existsChatRoomPort.existsChatRoomById(roomId)) {
            throw new NotExistsChatRoomException();
        }
        return loadNumOfUserByChatRoomPort.loadNumOfUserByChatRoom(roomId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long findUserIdByName(String name) {
        return loadUserPort.load(name).orElseThrow(() -> new NotFoundUserException(name)).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getUserNamesByChatRoomId(Long roomId) {
        if (!existsChatRoomPort.existsChatRoomById(roomId)) {
            throw new NotExistsChatRoomException();
        }
        return loadUserListOfChatRoomPort.findUserNamesByChatRoomId(roomId);
    }

    private static List<ChatRoomDto> getChatRoomDto(Page<ChatRoom> pages) {
        return pages.stream()
                .map(ChatRoomDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    private void saveEntryUserChatRoom(UserChatRoom userChatRoom) {
        saveUserChatRoomPort.save(userChatRoom);
    }

    @Transactional
    private void saveEntryChatRoom(ChatRoom chatRoom) {
        saveChatRoomPort.save(chatRoom);
    }
}