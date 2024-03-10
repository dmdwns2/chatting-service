package com.example.api;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomDto;
import com.example.dto.Response;
import com.example.service.ChatRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chattings")
public class ChatRoomRestController {

    private final ChatRoomService chatRoomService;

    @Tag(name = "create chatroom")
    @PostMapping("/rooms")
    public Response<String> create(@RequestBody @Valid ChatRoomCreateRequest chatRoomCreateRequest,
                                   HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.create(chatRoomCreateRequest, userId);
        return Response.success("created a chatroom");
    }

    @Tag(name = "get chatroom list (default : page = 0, size > 0)")
    @GetMapping("/rooms")
    public Response<String> getList(int page, int size) {
        Pageable pageable = new PageRequest(page, size, Sort.unsorted()) {
        };
        List<ChatRoomDto> allChatRooms = chatRoomService.getList(pageable);
        StringBuilder responseBuilder = new StringBuilder();
        if (allChatRooms.size() == 0) {
            return Response.error("there is no chat room");
        }
        for (ChatRoomDto room : allChatRooms) {
            responseBuilder.append("title : ").append(room.getTitle())
                    .append(" , owner : ").append(room.getOwner()).append("\n");
        }
        return Response.success(responseBuilder.toString());
    }

    @Tag(name = "join the chatroom")
    @PostMapping("/rooms/join/{roomId}")
    public Response<String> join(@PathVariable Long roomId, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.join(roomId, userId);
        return Response.success("enter the chat room");
    }

    @Tag(name = "leave chatroom")
    @DeleteMapping("/rooms/leave/{roomId}")
    public Response<String> leave(@PathVariable Long roomId, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.leave(roomId, userId);
        return Response.success("came out of the chat room");
    }

    @Tag(name = "check number of users", description = "Check the number of current chat room users")
    @GetMapping("/rooms/check/{roomId}")
    public Response<String> checkNumOfUser(@PathVariable Long roomId) {
        int numOfUsers = chatRoomService.loadNumOfUserByChatRoomId(roomId);
        return Response.success("number of users in the current chatroom : " + numOfUsers);
    }

    @Tag(name = "get user list", description = "get user list of current chat room")
    @GetMapping("/rooms/users/{roomId}")
    public Response<String> getUserList(@PathVariable Long roomId) {
        List<String> userNames = chatRoomService.getUserNamesByChatRoomId(roomId);
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : userNames) {
            stringBuilder.append(name).append(", ");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        return Response.success("current user list : " + stringBuilder);
    }
}
