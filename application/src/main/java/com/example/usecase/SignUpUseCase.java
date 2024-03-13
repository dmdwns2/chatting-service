package com.example.usecase;

import com.example.dto.SignUpCommand;
import com.example.dto.UserCreatedEvent;

public interface SignUpUseCase {
    UserCreatedEvent signup(SignUpCommand command);
}