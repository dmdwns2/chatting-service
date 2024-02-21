package com.example.api;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomDto;
import com.example.dto.Response;
import com.example.service.ChatRoomService;
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
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.create(chatRoomCreateRequest, userId);
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

    @PostMapping("/rooms/leave/{roomId}")
    public Response<String> join(@PathVariable Long roomId, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.join(roomId, userId);
        return Response.success("enter the chat room");
    }

    @DeleteMapping("/rooms/leave/{roomId}")
    public Response<String> exit(@PathVariable Long roomId, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.exit(roomId, userId);
        return Response.success("came out of the chat room");
    }
}
