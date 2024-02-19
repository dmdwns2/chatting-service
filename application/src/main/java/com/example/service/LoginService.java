package com.example.service;

import com.example.dto.LoginCommand;
import com.example.dto.LogoutCommand;
import com.example.dto.UserLoggedInEvent;
import com.example.dto.UserLoggedOutEvent;

public interface LoginService {
    UserLoggedInEvent login(LoginCommand command);

    UserLoggedOutEvent logout(LogoutCommand command);
}