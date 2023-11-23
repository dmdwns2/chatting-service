package com.example.usecase;

import com.example.dto.LoginCommand;
import com.example.dto.UserLoggedInEvent;

public interface LoginUseCase {
    UserLoggedInEvent login(LoginCommand command);
}