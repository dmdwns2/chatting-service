package com.example.controller;

import com.example.dto.ChatMsgDto;
import com.example.dto.ChatMsgRequest;
import com.example.dto.ChatMsgResponse;
import com.example.security.LoginCheck;
import com.example.service.ChatMsgService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chattings")
public class ChatMsgController {
    private final ChatMsgService chatMsgService;

    @PostMapping("/messages/{roomId}")
    @LoginCheck
    public void sendChat(@PathVariable Long roomId, @RequestBody @Valid ChatMsgRequest message
            , HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        chatMsgService.sendMessage(message, userId, roomId);
    }

    @GetMapping("/messages/{roomId}")
    @LoginCheck
    public void getChatList(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long lastId,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        chatMsgService.getChatMsgList(roomId, userId, lastId);
    }
}
