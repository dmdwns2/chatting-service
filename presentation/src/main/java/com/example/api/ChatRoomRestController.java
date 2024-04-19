package com.example.api;

import com.example.dto.ChatRoomCreateRequest;
import com.example.dto.ChatRoomDto;
import com.example.dto.Response;
import com.example.log4j.LogRunningTime;
import com.example.service.ChatRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chattings")
public class ChatRoomRestController {

    private final ChatRoomService chatRoomService;

    @LogRunningTime
    @Tag(name = "create chatroom")
    @PostMapping("/rooms")
    public Response<String> create(@RequestBody @Valid ChatRoomCreateRequest chatRoomCreateRequest,
                                   HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.create(chatRoomCreateRequest, userId);
        return Response.success("created a chatroom");
    }

    @LogRunningTime
    @Tag(name = "get chatroom list (default : page = 0, size > 0)")
    @GetMapping("/rooms")
    public Response<String> getList(int page, int size) {
        Pageable pageable = new PageRequest(page, size, Sort.unsorted()) {
        };
        List<ChatRoomDto> allChatRooms = chatRoomService.getList(pageable);
        if (allChatRooms.size() == 0) {
            return Response.error("there is no chat room");
        }
        return Response.success(allChatRooms.stream()
                .map(room -> "title : " + room.getTitle() + " , owner : " + room.getOwner())
                .collect(Collectors.joining("\n")));
    }

    @LogRunningTime
    @Tag(name = "join the chatroom")
    @PostMapping("/rooms/join/{roomId}")
    public Response<String> join(@PathVariable Long roomId, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.join(roomId, userId);
        return Response.success("enter the chat room");
    }

    @LogRunningTime
    @Tag(name = "leave chatroom")
    @DeleteMapping("/rooms/leave/{roomId}")
    public Response<String> leave(@PathVariable Long roomId, HttpSession session) {
        String name = session.getAttribute("user").toString();
        Long userId = chatRoomService.findUserIdByName(name);
        chatRoomService.leave(userId, roomId);
        return Response.success("came out of the chat room");
    }

    @LogRunningTime
    @Tag(name = "check number of users", description = "Check the number of current chat room users")
    @GetMapping("/rooms/check/{roomId}")
    public Response<String> checkNumOfUser(@PathVariable Long roomId) {
        int numOfUsers = chatRoomService.loadNumOfUserByChatRoomId(roomId);
        return Response.success("number of users in the current chatroom : " + numOfUsers);
    }

    @LogRunningTime
    @Tag(name = "get user list", description = "get user list of current chat room")
    @GetMapping("/rooms/users/{roomId}")
    public Response<String> getUserList(@PathVariable Long roomId) {
        List<String> userNames = chatRoomService.getUserNamesByChatRoomId(roomId);
        return Response.success("current user list : "
                + String.join(", ", userNames));
    }
}
