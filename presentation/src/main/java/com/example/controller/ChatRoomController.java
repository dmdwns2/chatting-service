package com.example.controller;

import com.example.ChatRoomService;
import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chattings")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/rooms")
    public Response<String> create(@RequestBody @Valid ChatRoomCreateRequest chatRoomCreateRequest,
                                   Authentication authentication) {
        String name = authentication.getName();
        chatRoomService.create(chatRoomCreateRequest, name);
        return Response.success("created a chatroom");
    }


}
