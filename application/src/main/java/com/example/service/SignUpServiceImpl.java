package com.example.service;

import com.example.dto.SignUpCommand;
import com.example.dto.UserCreatedEvent;
import com.example.exception.DuplicateNameException;
import com.example.exception.DuplicateNicknameException;
import com.example.model.User;
import com.example.port.CurrentDataTimePort;
import com.example.port.ExistsNamePort;
import com.example.port.ExistsNicknamePort;
import com.example.port.SaveUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final ExistsNamePort existsNamePort;
    private final ExistsNicknamePort existsNicknamePort;
    private final SaveUserPort saveUserPort;
    private final CurrentDataTimePort currentDataTimePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserCreatedEvent signup(final SignUpCommand command) {
        validate(command);
        final String hashedPassword = passwordEncoder.encode(command.getPassword());

        User user = User.builder()
                .name(command.getName())
                .password(hashedPassword)
                .nickname(command.getNickname())
                .isLogin(false)
                .build();

        saveUserPort.save(user);
        return new UserCreatedEvent(user.getName(), currentDataTimePort.now());
    }

    private void validate(final SignUpCommand command) {
        if (command.getName().length() > 20 || command.getName().length() < 3) {
            throw new RuntimeException("The ID should be at least 3 characters and no more than 20 characters.");
        }
        if (command.getPassword().length() > 50 || command.getPassword().length() < 4) {
            throw new RuntimeException("The password should be at least 4 characters and no more than 50 characters.");
        }
        if (command.getNickname().length() > 8 || command.getNickname().isEmpty()) {
            throw new RuntimeException("The nickname should be at least 1 character and no more than 8 characters.");
        }
        if (existsNamePort.existsName(command.getName())) {
            throw new DuplicateNameException(command.getName());
        }
        if (existsNicknamePort.existsNickname(command.getNickname())) {
            throw new DuplicateNicknameException(command.getNickname());
        }
    }
}