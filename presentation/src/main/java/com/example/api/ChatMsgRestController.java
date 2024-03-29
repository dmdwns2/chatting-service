package com.example.api;

import com.example.dto.ChatMsgDto;
import com.example.dto.ChatMsgRequest;
import com.example.dto.ChatMsgResponse;
import com.example.exception.NotExistsClientException;
import com.example.security.LoginCheck;
import com.example.service.ChatMsgService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chattings")
public class ChatMsgRestController {

    private final ChatMsgService chatMsgService;

    @PostMapping("/messages/{roomId}")
    @LoginCheck
    @Tag(name = "send message")
    public ResponseEntity<Object> sendChat(
            @PathVariable Long roomId,
            @RequestBody @Valid ChatMsgRequest message,
            HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatMsgService.findUserIdByName(name);
        ChatMsgResponse response = chatMsgService.sendMessage(message, userId, roomId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/messages/{roomId}")
    @LoginCheck
    @Tag(name = "get message list")
    public ResponseEntity<Object> getChatList(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long lastId,
            HttpSession session) {
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