package com.example.websocket;

import com.example.exception.ChatErrorException;
import com.example.exception.NotExistsClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class SocketTextHandler extends TextWebSocketHandler {
    private final Map<Long, List<WebSocketSession>> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long roomId = extractRoomId(session);
        List<WebSocketSession> roomSessions = sessions.getOrDefault(roomId, new ArrayList<>());

        roomSessions.add(session);
        sessions.put(roomId, roomSessions);
        log.info("client connection established. session : {} + roomId : {}", session, roomId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long roomId = extractRoomId(session);
        List<WebSocketSession> roomSessions = sessions.get(roomId);

        if (roomSessions != null) {
            roomSessions.remove(session);
            log.info("client connection closed. session : {} + roomId : {}", session, roomId);
        } else
            log.info("failed to client connection closed. session : {} + roomId : {}", session, roomId);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        Long roomId = extractRoomId(session);
        List<WebSocketSession> roomSessions = sessions.get(roomId);
        if (Optional.ofNullable(roomSessions).isPresent()) {
            String payload = message.getPayload().toString();
            log.info("message : {} + roomId : {} + session : {}", payload, roomId, session);
            for (WebSocketSession msg : roomSessions) {
                try {
                    msg.sendMessage(message);
                } catch (IOException e) {
                    throw new ChatErrorException();
                }
            }
        } else {
            throw new NotExistsClientException();
        }
    }

    private Long extractRoomId(WebSocketSession session) {
        Long roomId = null;
        String uri = Objects.requireNonNull(session.getUri()).toString();
        String[] uriParts = uri.split("/");
        // /chattings/message/{roomId} 일 때 roomId 추출
        if (uriParts.length >= 4 && uriParts[2].equals("message")) {
            return Long.valueOf(uriParts[3]);
        }
        // /chattings/room/join/{roomId}, /chattings/room/leave/{roomId} 일 때 roomId 추출
        if (uriParts.length >= 5 && uriParts[2].equals("room") &&
                (uriParts[3].equals("join") || uriParts[3].equals("leave"))) {
            roomId = Long.valueOf(uriParts[4]);
        }
        return roomId;
    }
}
