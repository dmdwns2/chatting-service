package com.example.usecase.impl;

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
        if(existsNamePort.existsName(command.getName())) {
            throw new DuplicateNameException(command.getName());
        }
        if(existsNicknamePort.existsNickname(command.getNickname())) {
            throw new DuplicateNicknameException(command.getNickname());
        }

        User user = User.of(command.getName(), command.getPassword(), command.getNickname());

        saveUserPort.save(user);

        return new UserCreatedEvent(user.getName(), currentDataTimePort.now());
    }
}