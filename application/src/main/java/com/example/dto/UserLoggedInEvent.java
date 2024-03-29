package com.example.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserLoggedInEvent {
    String name;
    LocalDateTime createdAt;
}