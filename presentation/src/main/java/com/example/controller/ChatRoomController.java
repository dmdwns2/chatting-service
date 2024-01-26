package com.example.controller;

import com.example.ChatRoomService;
import com.example.dto.ChatRoomCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chattings")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("chatRoomCreateRequest", new ChatRoomCreateRequest());
        return "createForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute ChatRoomCreateRequest form, BindingResult result,
                         Authentication authentication) {
        if (result.hasErrors()) {
            return "createForm";
        }
        String name = authentication.getName();
        chatRoomService.create(form, name);
        return "chatroomlist";
    }

    @PostMapping("/rooms/join/{owner}")
    public String join(@PathVariable String owner, Authentication authentication) {
        String name = authentication.getName();
        chatRoomService.join(owner, name);
        return "home"; // TODO : home -> chatroom
    }
}
