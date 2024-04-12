package com.example.service;

import com.example.dto.SignUpCommand;
import com.example.dto.UserCreatedEvent;

public interface SignUpService {
    UserCreatedEvent signup(SignUpCommand command);
}