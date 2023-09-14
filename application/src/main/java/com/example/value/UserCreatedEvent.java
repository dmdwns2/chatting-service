package com.example.value;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserCreatedEvent {
    String name;
    LocalDateTime createdAt;
}
