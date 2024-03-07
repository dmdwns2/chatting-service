package com.example.controller;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomDto;
import com.example.service.ChatRoomService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                         HttpSession session) {
        if (result.hasErrors()) {
            return "createForm";
        }
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.create(form, userId);
        return "chatroomlist";
    }

    @PostMapping("/rooms/join/{roomId}")
    public String join(@PathVariable Long roomId, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.join(roomId, userId);
        return "chatroom";
    }

    @DeleteMapping("/rooms/leave/{roomId}")
    public String leave(@PathVariable Long roomId, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.leave(roomId, userId);
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
