package com.example.dto;

import lombok.Value;

@Value
public class UserLoggedInEvent {
    String name;
    String token;
}