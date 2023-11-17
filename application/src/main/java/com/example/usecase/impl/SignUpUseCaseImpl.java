package com.example.usecase.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dto.SignUpCommand;
import com.example.dto.UserCreatedEvent;
import com.example.exception.DuplicateNameException;
import com.example.exception.DuplicateNicknameException;
import com.example.model.User;
import com.example.port.CurrentDataTimePort;
import com.example.port.ExistsNamePort;
import com.example.port.ExistsNicknamePort;
import com.example.port.SaveUserPort;
import com.example.stereotype.UseCase;
import com.example.usecase.SignUpUseCase;
import jakarta.transaction.Transactional;

@UseCase
public class SignUpUseCaseImpl implements SignUpUseCase {
    private final ExistsNamePort existsNamePort;
    private final ExistsNicknamePort existsNicknamePort;
    private final SaveUserPort saveUserPort;
    private final CurrentDataTimePort currentDataTimePort;

    public SignUpUseCaseImpl(ExistsNamePort existsNamePort, ExistsNicknamePort existsNicknamePort, SaveUserPort saveUserPort, CurrentDataTimePort currentDataTimePort) {
        this.existsNamePort = existsNamePort;
        this.existsNicknamePort = existsNicknamePort;
        this.saveUserPort = saveUserPort;
        this.currentDataTimePort = currentDataTimePort;
    }

    @Transactional
    @Override
    public UserCreatedEvent join(SignUpCommand command) {
        if (command.getName().length() > 20 || command.getName().length() < 3) {
            throw new RuntimeException("The ID should be at least 3 characters and no more than 20 characters.");
        }
        if (command.getPassword().length() > 50 || command.getPassword().length() < 4) {
            throw new RuntimeException("The password should be at least 4 characters and no more than 50 characters.");
        }
        if (command.getNickname().length() > 8 || command.getNickname().length() < 1) {
            throw new RuntimeException("The nickname should be at least 1 character and no more than 8 characters.");
        }
        if (existsNamePort.existsName(command.getName())) {
            throw new DuplicateNameException(command.getName());
        }
        if (existsNicknamePort.existsNickname(command.getNickname())) {
            throw new DuplicateNicknameException(command.getNickname());
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, command.getPassword().toCharArray());
        BCrypt.Result result = BCrypt.verifyer().verify(command.getPassword().toCharArray(), hashedPassword);
        if (!result.verified) {
            throw new RuntimeException("An error occurred during the password hashing process.");
        }

        User user = User.of(command.getName(), hashedPassword, command.getNickname());

        saveUserPort.save(user);

        return new UserCreatedEvent(user.getName(), currentDataTimePort.now());
    }
}