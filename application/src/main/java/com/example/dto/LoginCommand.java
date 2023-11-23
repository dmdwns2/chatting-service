package com.example.dto;

import lombok.Value;

@Value
public class LoginCommand {
    String name;
    String password;
}
