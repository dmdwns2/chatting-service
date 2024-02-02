package com.example.controller;

import com.example.ChatRoomService;
import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/rooms/{owner}/join")
    public String join(@PathVariable String owner, Authentication authentication) {
        String name = authentication.getName();
        chatRoomService.join(owner, name);
        return "home"; // TODO : home -> chatroom
    }

    @DeleteMapping("/rooms/{owner}/leave")
    public String exit(@PathVariable String owner, Authentication authentication) {
        String name = authentication.getName();
        chatRoomService.exit(owner, name);
        return "redirect:/chattings/rooms";
    }

    @GetMapping("/rooms")
    public String getList(Model model, Pageable pageable) {
        List<ChatRoomDto> allChatRooms = chatRoomService.getList(pageable);
        return paging(model, convertListToPage(allChatRooms, 0, 10));
    }

    private Page<ChatRoomDto> convertListToPage(List<ChatRoomDto> list, int page, int size) {
        int start = (int) Math.min((long) page * size, (long) list.size());
        int end = (int) Math.min((long) (page + 1) * size, (long) list.size());
        List<ChatRoomDto> sublist = list.subList(start, end);

        return new PageImpl<>(sublist, PageRequest.of(page, size), list.size());
    }

    private String paging(Model model, Page<ChatRoomDto> allChatRooms) {
        int nowPage = allChatRooms.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, allChatRooms.getTotalPages());
        int lastPage = allChatRooms.getTotalPages();

        model.addAttribute("allChatRooms", allChatRooms);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("lastPage", lastPage);
        return "chatroomlist";
    }
}
