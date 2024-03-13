package com.example.usecase.impl;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.exception.ExistsChatRoomException;
import com.example.model.User;
import com.example.port.*;
import com.example.service.ChatRoomServiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ChatRoomServiceServiceImplTest {
    @Mock
    private ExistsChatRoomPort existsChatRoomPort;

    @Mock
    private SaveChatRoomPort saveChatRoomPort;

    @Mock
    private LoadChatRoomPort loadChatRoomPort;

    @Mock
    private CurrentDataTimePort currentDataTimePort;

    @Mock
    private LoadUserPort loadUserPort;

    @Mock
    private SaveUserChatRoomPort saveUserChatRoomPort;

    @Mock
    private DeleteUserChatRoomByUserIdPort deleteUserChatRoomByUserIdPort;

    @Mock
    private DeleteChatRoomPort deleteChatRoomPort;

    @Mock
    private LoadNumOfUserByChatRoomPort loadNumOfUserByChatRoomPort;

    @Mock
    private ExsistUserChatRoomPort exsistUserChatRoomPort;

    @Mock
    private LoadUserListOfChatRoomPort loadUserListOfChatRoomPort;

    private ChatRoomServiceServiceImpl chatRoomServiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        chatRoomServiceService = new ChatRoomServiceServiceImpl(
                existsChatRoomPort,
                saveChatRoomPort,
                loadChatRoomPort,
                currentDataTimePort,
                loadUserPort,
                saveUserChatRoomPort,
                deleteUserChatRoomByUserIdPort,
                deleteChatRoomPort,
                loadNumOfUserByChatRoomPort,
                exsistUserChatRoomPort,
                loadUserListOfChatRoomPort
        );
    }

    @DisplayName("채팅방 생성 성공")
    @Test
    void create_success() {
        Long userId = 1L;
        ChatRoomCreateRequest command = new ChatRoomCreateRequest("아무나");
        when(existsChatRoomPort.existsChatRoomByOwner(userId)).thenReturn(false);
        when(currentDataTimePort.now()).thenReturn(LocalDateTime.now());
        when(loadUserPort.load(userId)).thenReturn(Optional.of(
                User.of(userId,"m","n","s",false)));

        ChatRoomCreatedEvent result = chatRoomServiceService.create(command, userId);

        assertThat(command.getTitle()).isEqualTo(result.getTitle());
        assertThat(userId).isEqualTo(result.getOwner());
    }

    @DisplayName("이미 채팅방이 존재하는 경우 에러 발생")
    @Test
    void create_exists_chatroom() {
        Long userId = 1L;
        ChatRoomCreateRequest command = new ChatRoomCreateRequest("아무나");
        when(existsChatRoomPort.existsChatRoomByOwner(userId)).thenReturn(true);
        when(loadUserPort.load(userId)).thenReturn(Optional.of(
                User.of(userId,"m","n","s",false)));

        assertThatThrownBy(() -> chatRoomServiceService.create(command, userId))
                .isInstanceOf(ExistsChatRoomException.class)
                .hasMessage("There is a chatroom that already exists.");

        verify(existsChatRoomPort, times(1)).existsChatRoomByOwner(any());
    }
}