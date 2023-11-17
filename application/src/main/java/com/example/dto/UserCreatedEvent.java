package com.example.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserCreatedEvent {
    String name;
    LocalDateTime createdAt;
}