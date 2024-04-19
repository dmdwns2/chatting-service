package com.example.controller;

import com.example.dto.ChatMsgRequest;
import com.example.log4j.LogRunningTime;
import com.example.security.LoginCheck;
import com.example.service.ChatMsgService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chattings")
public class ChatMsgController {
    private final ChatMsgService chatMsgService;

    @LogRunningTime
    @PostMapping("/messages/{roomId}")
    @LoginCheck
    public void sendChat(@PathVariable Long roomId, @RequestBody @Valid ChatMsgRequest message
            , HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatMsgService.findUserIdByName(name);
        chatMsgService.sendMessage(message, userId, roomId);
    }

    @LogRunningTime
    @GetMapping("/messages/{roomId}")
    @LoginCheck
    public void getChatList(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long lastId,
            HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatMsgService.findUserIdByName(name);
        chatMsgService.getChatMsgList(roomId, userId, lastId);
    }

}
