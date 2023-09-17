package com.example.usecase.impl;

import com.example.dto.UserCreatedEvent;
import com.example.exception.DuplicateNameException;
import com.example.exception.DuplicateNicknameException;
import com.example.port.CurrentDataTimePort;
import com.example.port.ExistsNamePort;
import com.example.port.ExistsNicknamePort;
import com.example.port.SaveUserPort;
import com.example.dto.SignUpCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignUpUseCaseImplTest {

    @Mock
    private ExistsNamePort existsNamePort;

    @Mock
    private ExistsNicknamePort existsNicknamePort;

    @Mock
    private SaveUserPort saveUserPort;

    @Mock
    private CurrentDataTimePort currentDataTimePort;

    private SignUpUseCaseImpl signUpUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        signUpUseCase = new SignUpUseCaseImpl(
                existsNamePort,
                existsNicknamePort,
                saveUserPort,
                currentDataTimePort
        );
    }

    @Test
    void join_ValidUser_ReturnsUserCreatedEvent() {
        SignUpCommand command = new SignUpCommand("messi", "2022wc", "leo");
        when(existsNamePort.existsName(command.getName())).thenReturn(false);
        when(existsNicknamePort.existsNickname(command.getNickname())).thenReturn(false);
        when(currentDataTimePort.now()).thenReturn(LocalDateTime.now());

        UserCreatedEvent result = signUpUseCase.join(command);

        assertNotNull(result);
        Assertions.assertThat(command.getName()).isEqualTo(result.getName());
    }


    @Test
    void join_DuplicateName_ThrowsDuplicateNameException() {
        SignUpCommand command = new SignUpCommand("messi", "2022wc", "leo");
        when(existsNamePort.existsName(command.getName())).thenReturn(true);

        assertThrows(DuplicateNameException.class, () -> signUpUseCase.join(command));
    }

    @Test
    void join_DuplicateNickname_ThrowsDuplicateNicknameException() {
        SignUpCommand command = new SignUpCommand("messi", "2022wc", "leo");
        when(existsNicknamePort.existsNickname(command.getNickname())).thenReturn(true);

        assertThrows(DuplicateNicknameException.class, () -> signUpUseCase.join(command));
    }
}