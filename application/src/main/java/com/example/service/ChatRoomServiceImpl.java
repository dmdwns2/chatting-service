package com.example.service;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.dto.ChatRoomDto;
import com.example.exception.ExistsChatRoomException;
import com.example.exception.NotExistsChatRoomException;
import com.example.exception.NotExistsUserInChatRoom;
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
public class ChatRoomServiceImpl implements ChatRoomService {

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

    @Transactional
    @Override
    public ChatRoomCreatedEvent create(final ChatRoomCreateRequest chatRoomCreateRequest, final Long userId) {
        if (existsChatRoomPort.existsChatRoomByOwner(userId)) {
            throw new ExistsChatRoomException();
        }
        final User user = loadUserPort.loadById(userId).orElseThrow(() -> new NotFoundUserException(userId.toString()));
        ChatRoom chatRoom = ChatRoom.builder()
                .owner(user.getId())
                .title(chatRoomCreateRequest.getTitle())
                .build();
        saveChatRoomPort.save(chatRoom);
        return new ChatRoomCreatedEvent(chatRoom.getOwner(), chatRoom.getTitle(), currentDataTimePort.now());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ChatRoomDto> getList(final Pageable pageable) {
        return getChatRoomDto(loadChatRoomPort.loadChatRoomPage(pageable));
    }

    @Transactional
    @Override
    public void join(final Long roomId, final Long userId) {
        if (!existsChatRoomPort.existsChatRoomById(roomId)) {
            throw new NotExistsChatRoomException();
        }
        if (existsUserChatRoomPort.existsByUserIdAndChatRoomId(userId, roomId)) {
            throw new ExistsChatRoomException();
        }
        final ChatRoom chatRoom = loadChatRoomPort.loadById(roomId)
                .orElseThrow(NotExistsChatRoomException::new);
        final User user = loadUserPort.loadById(userId).orElseThrow(()
                -> new NotFoundUserException(userId.toString()));

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
        saveUserChatRoomPort.save(userChatRoom);
    }

    @Override
    @Transactional
    public void leave(final Long userId, final Long roomId) {
        if (!existsUserChatRoomPort.existsByUserIdAndChatRoomId(userId, roomId)) {
            throw new NotExistsUserInChatRoom();
        }
        final ChatRoom chatRoom = loadChatRoomPort.loadById(roomId)
                .orElseThrow(NotExistsChatRoomException::new);

        if (Objects.equals(chatRoom.getOwner(), userId)) {
            deleteUserChatRoomByUserIdPort.deleteByUserIdAndChatRoomId(userId, roomId);
            deleteChatRoomPort.deleteById(roomId);
        }
        if (!Objects.equals(chatRoom.getId(), userId)) {
            deleteUserChatRoomByUserIdPort.deleteByUserIdAndChatRoomId(userId, roomId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int loadNumOfUserByChatRoomId(final Long roomId) {
        if (!existsChatRoomPort.existsChatRoomById(roomId)) {
            throw new NotExistsChatRoomException();
        }
        return loadNumOfUserByChatRoomPort.loadNumOfUsersByChatRoom(roomId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long findUserIdByName(final String name) {
        return loadUserPort.loadByName(name).orElseThrow(() -> new NotFoundUserException(name)).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getUserNamesByChatRoomId(final Long roomId) {
        if (!existsChatRoomPort.existsChatRoomById(roomId)) {
            throw new NotExistsChatRoomException();
        }
        return loadUserListOfChatRoomPort.loadUserNamesByChatRoomId(roomId);
    }

    private static List<ChatRoomDto> getChatRoomDto(final Page<ChatRoom> pages) {
        return pages.stream()
                .map(ChatRoomDto::of)
                .collect(Collectors.toList());
    }
}