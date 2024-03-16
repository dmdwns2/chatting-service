package com.example.service;

import com.example.LoadChatMsgCustomPort;
import com.example.dto.ChatMsgRequest;
import com.example.dto.ChatMsgResponse;
import com.example.model.ChatRoom;
import com.example.model.User;
import com.example.port.ExistsUserChatRoomPort;
import com.example.port.LoadChatRoomPort;
import com.example.port.LoadUserPort;
import com.example.port.SaveChatMsgPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ChatMsgServiceImplTest {
    @Mock
    private SaveChatMsgPort saveChatMsgPort;

    @Mock
    private LoadUserPort loadUserPort;

    @Mock
    private LoadChatRoomPort loadChatRoomPort;

    @Mock
    private LoadChatMsgCustomPort loadChatMsgCustomPort;

    @Mock
    private ExistsUserChatRoomPort existsUserChatRoomPort;

    private ChatMsgServiceImpl chatMsgService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        chatMsgService = new ChatMsgServiceImpl(
                saveChatMsgPort,
                loadUserPort,
                loadChatRoomPort,
                loadChatMsgCustomPort,
                existsUserChatRoomPort
        );
    }

    @DisplayName("메세지 전송 성공")
    @Test
    void sendMsg() {
        Long userId = 1L;
        Long roomId = 1L;
        ChatMsgRequest request = new ChatMsgRequest("안녕");
        when(loadUserPort.loadById(userId)).thenReturn(Optional.of(
                User.of(userId, "user", "n", "nickname", false)));
        when(loadChatRoomPort.loadById(roomId)).thenReturn(Optional.of(
                ChatRoom.of(roomId, "아무나", 2L)));

        ChatMsgResponse chatMsgResponse = chatMsgService.sendMessage(request, userId, roomId);
        assertThat(chatMsgResponse.getMessage()).isEqualTo("안녕");
        assertThat(chatMsgResponse.getOwner()).isEqualTo(2L);
        assertThat(chatMsgResponse.getFrom()).isEqualTo("nickname");
    }
}