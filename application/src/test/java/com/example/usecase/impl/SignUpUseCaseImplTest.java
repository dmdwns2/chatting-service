package com.example.usecase.impl;

import com.example.dto.SignUpCommand;
import com.example.dto.UserCreatedEvent;
import com.example.exception.DuplicateNameException;
import com.example.exception.DuplicateNicknameException;
import com.example.port.CurrentDataTimePort;
import com.example.port.ExistsNamePort;
import com.example.port.ExistsNicknamePort;
import com.example.port.SaveUserPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private PasswordEncoder passwordEncoder;

    private SignUpUseCaseImpl signUpUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        signUpUseCase = new SignUpUseCaseImpl(
                existsNamePort,
                existsNicknamePort,
                saveUserPort,
                currentDataTimePort,
                passwordEncoder);
    }

    @Test
    void join_ValidUser_ReturnsUserCreatedEvent() {
        SignUpCommand command = new SignUpCommand("messi", "2022wc", "leo");
        when(existsNamePort.existsName(command.getName())).thenReturn(false);
        when(existsNicknamePort.existsNickname(command.getNickname())).thenReturn(false);
        when(currentDataTimePort.now()).thenReturn(LocalDateTime.now());

        UserCreatedEvent result = signUpUseCase.signup(command);

        assertNotNull(result);
        Assertions.assertThat(command.getName()).isEqualTo(result.getName());
    }

    @Test
    void join_InvalidName_ThrowsRuntimeException() {
        SignUpCommand command = new SignUpCommand("messssssssssssssssssssssi", "2022wc", "leo");

        assertThrows(RuntimeException.class, () -> signUpUseCase.signup(command));
    }

    @Test
    void join_InvalidPassword_ThrowsRuntimeException() {
        SignUpCommand command = new SignUpCommand("messi", "2022wcwinner2022wcwinner2022wcwinner2022wcwinner2022wcwinner2022" +
                "wcwinner2022wcwinner2022wcwinner2022wcwinner2022wcwinner2022wcwinner", "leo");

        assertThrows(RuntimeException.class, () -> signUpUseCase.signup(command));
    }

    @Test
    void join_InvalidNickname_ThrowsRuntimeException() {
        SignUpCommand command = new SignUpCommand("messi", "2022wc", "leoooooooooooooooooooo");

        assertThrows(RuntimeException.class, () -> signUpUseCase.signup(command));
    }

    @Test
    void join_DuplicateName_ThrowsDuplicateNameException() {
        SignUpCommand command = new SignUpCommand("messi", "2022wc", "leo");
        when(existsNamePort.existsName(command.getName())).thenReturn(true);

        assertThrows(DuplicateNameException.class, () -> signUpUseCase.signup(command));
    }

    @Test
    void join_DuplicateNickname_ThrowsDuplicateNicknameException() {
        SignUpCommand command = new SignUpCommand("messi", "2022wc", "leo");
        when(existsNicknamePort.existsNickname(command.getNickname())).thenReturn(true);

        assertThrows(DuplicateNicknameException.class, () -> signUpUseCase.signup(command));
    }
}