package com.example.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ChatRoomCreatedEvent {
    Long owner;
    String title;
    LocalDateTime createdAt;
}