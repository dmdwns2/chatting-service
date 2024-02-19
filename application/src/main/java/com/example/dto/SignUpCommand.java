package com.example.dto;

import lombok.Value;

@Value
public class SignUpCommand {
    Long id;
    String name;
    String password;
    String nickname;
}