package com.example.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserLoggedOutEvent {
    String name;
    LocalDateTime createdAt;
}