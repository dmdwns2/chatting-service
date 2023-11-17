package com.example.dto;

import lombok.Value;

@Value
public class SignUpCommand {
    String name;
    String password;
    String nickname;
}