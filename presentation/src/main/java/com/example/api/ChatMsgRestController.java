package com.example.api;

import com.example.dto.ChatMsgDto;
import com.example.dto.ChatMsgRequest;
import com.example.dto.ChatMsgResponse;
import com.example.exception.NotExistsClientException;
import com.example.log4j.LogRunningTime;
import com.example.security.LoginCheck;
import com.example.service.ChatMsgService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chattings")
public class ChatMsgRestController {

    private final ChatMsgService chatMsgService;

    @LogRunningTime
    @LoginCheck
    @Tag(name = "send message")
    @PostMapping("/messages/{roomId}")
    public ResponseEntity<Object> sendChat(
            @PathVariable Long roomId,
            @RequestBody @Valid final ChatMsgRequest message, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatMsgService.findUserIdByName(name);
        ChatMsgResponse response = chatMsgService.sendMessage(message, userId, roomId);
        return ResponseEntity.ok(response);
    }

    @LogRunningTime
    @LoginCheck
    @Tag(name = "get message list")
    @GetMapping("/messages/{roomId}")
    public ResponseEntity<Object> getChatList(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long lastId, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatMsgService.findUserIdByName(name);
        List<ChatMsgDto> roomChatMsgList = chatMsgService.getChatMsgList(roomId, userId, lastId);
        if (roomChatMsgList != null) {
            return ResponseEntity.ok(roomChatMsgList);
        } else {
            throw new NotExistsClientException();
        }
    }
}