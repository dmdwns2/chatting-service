package com.example.usecase;

import com.example.value.SignUpCommand;
import com.example.value.UserCreatedEvent;

public interface SignUpUseCase {
    UserCreatedEvent join(SignUpCommand command);
}