package com.example.service;

import com.example.dto.LoginCommand;
import com.example.dto.LogoutCommand;
import com.example.dto.UserLoggedInEvent;
import com.example.dto.UserLoggedOutEvent;
import com.example.exception.NotFoundUserException;
import com.example.exception.NotMatchPasswordException;
import com.example.model.User;
import com.example.port.LoadUserPort;
import com.example.port.SaveUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;

    @Override
    public UserLoggedInEvent login(LoginCommand command) {
        validateCommand(command);
        User user = loadUserPort.loadByName(command.getName())
                .orElseThrow(() -> new NotFoundUserException(command.getName()));
        validatePasswordMatching(command.getPassword(), user.getPassword());
        user.setIsLogin(true);
        saveUserPort.save(user);
        return new UserLoggedInEvent(user.getName(), LocalDateTime.now());
    }

    @Override
    public UserLoggedOutEvent logout(LogoutCommand command) {
        User user = loadUserPort.loadByName(command.getName())
                .orElseThrow(() -> new NotFoundUserException(command.getName()));
        user.setIsLogin(false);
        saveUserPort.save(user);
        return new UserLoggedOutEvent(user.getName(), LocalDateTime.now());
    }

    private void validateCommand(LoginCommand command) {
        if (command.getName().length() > 20 || command.getName().length() < 3) {
            throw new RuntimeException("Invalid input. please try again");
        }
        if (command.getPassword().length() > 50 || command.getPassword().length() < 4) {
            throw new RuntimeException("Invalid input. please try again");
        }
    }

    private void validatePasswordMatching(String rawPassword, String encodedPassword) {
        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            return;
        }
        throw new NotMatchPasswordException();
    }
}