package com.example.service;

import com.example.dto.LoginCommand;
import com.example.dto.LogoutCommand;
import com.example.dto.UserLoggedInEvent;
import com.example.dto.UserLoggedOutEvent;
import com.example.entity.UserJPAEntity;
import com.example.exception.NotFoundUserException;
import com.example.exception.NotMatchPasswordException;
import com.example.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserLoggedInEvent login(LoginCommand command) {
        validateCommand(command);
        UserJPAEntity user = userRepository.findByName(command.getName())
                .orElseThrow(() -> new NotFoundUserException(command.getName()));
        validatePasswordMatching(command.getPassword(), user.getPassword());
        user.setIsLogin(true);
        userRepository.save(user);
        return new UserLoggedInEvent(user.getName(), LocalDateTime.now());
    }

    @Transactional
    @Override
    public UserLoggedOutEvent logout(LogoutCommand command) {
        UserJPAEntity user = userRepository.findByName(command.getName())
                .orElseThrow(() -> new NotFoundUserException(command.getName()));
        user.setIsLogin(false);
        userRepository.save(user);
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