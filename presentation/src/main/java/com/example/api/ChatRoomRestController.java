package com.example.api;

import com.example.ChatRoomService;
import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomDto;
import com.example.dto.Response;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chattings")
public class ChatRoomRestController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/rooms")
    public Response<String> create(@RequestBody @Valid ChatRoomCreateRequest chatRoomCreateRequest,
                                   HttpSession session) {
        String name = session.getAttribute("user").toString();
        chatRoomService.create(chatRoomCreateRequest, name);
        return Response.success("created a chatroom");
    }

    @GetMapping("/rooms")
    public Response<String> getList(Pageable pageable) {
        List<ChatRoomDto> allChatRooms = chatRoomService.getList(pageable);
        if (allChatRooms.size() == 0) {
            return Response.error("there is no chat room");
        }
        return Response.success("got a list of chat rooms");
    }

    @PostMapping("/rooms/{owner}/join")
    public Response<String> join(@PathVariable String owner, HttpSession session) {
        String name = session.getAttribute("user").toString();
        chatRoomService.join(owner, name);
        return Response.success("enter the chat room");
    }

    @DeleteMapping("/room/{owner}/exit")
    public Response<String> exit(@PathVariable String owner, HttpSession session) {
        String name = session.getAttribute("user").toString();
        chatRoomService.exit(owner, name);
        return Response.success("came out of the chat room");
    }
}
