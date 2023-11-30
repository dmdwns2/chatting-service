package com.example.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ChatRoomCreatedEvent {
    String name;
    String title;
    LocalDateTime createdAt;
}