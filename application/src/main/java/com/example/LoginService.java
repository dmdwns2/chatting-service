package com.example;

import com.example.dto.LoginCommand;
import com.example.dto.UserLoggedInEvent;

public interface LoginService {
    UserLoggedInEvent login(LoginCommand command);
}