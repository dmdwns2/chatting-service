package com.example.usecase.impl;

import com.example.ChatRoomServiceServiceImpl;
import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomCreatedEvent;
import com.example.exception.ExistsChatRoomException;
import com.example.port.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

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

    private ChatRoomServiceServiceImpl chatRoomUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        chatRoomUseCase = new ChatRoomServiceServiceImpl(
                existsChatRoomPort,
                saveChatRoomPort,
                loadChatRoomPort,
                currentDataTimePort,
                loadUserPort,
                saveUserChatRoomPort
        );
    }

    @DisplayName("채팅방 생성 성공")
    @Test
    void create_success() {
        String name = "messi";
        ChatRoomCreateRequest command = new ChatRoomCreateRequest("아무나");
        when(existsChatRoomPort.existsChatRoom(command.getTitle())).thenReturn(false);
        when(currentDataTimePort.now()).thenReturn(LocalDateTime.now());

        ChatRoomCreatedEvent result = chatRoomUseCase.create(command, name);

        assertThat(command.getTitle()).isEqualTo(result.getTitle());
        assertThat(name).isEqualTo(result.getName());
    }

    @DisplayName("이미 채팅방이 존재하는 경우 에러 발생")
    @Test
    void create_exists_chatroom() {
        String name = "messi";
        ChatRoomCreateRequest command = new ChatRoomCreateRequest("아무나");
        when(existsChatRoomPort.existsChatRoom(name)).thenReturn(true);

        assertThatThrownBy(() -> chatRoomUseCase.create(command, name))
                .isInstanceOf(ExistsChatRoomException.class)
                .hasMessage("There is a chatroom that already exists.");

        verify(existsChatRoomPort, times(1)).existsChatRoom(anyString());
    }
}