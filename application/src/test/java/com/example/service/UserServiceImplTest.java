package com.example.service;

import com.example.dto.LoginCommand;
import com.example.dto.UserLoggedInEvent;
import com.example.exception.NotMatchPasswordException;
import com.example.model.User;
import com.example.port.LoadUserPort;
import com.example.port.SaveUserPort;
import com.example.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserServiceImpl loginUseCase;

    @Mock
    private LoadUserPort loadUserPort;

    @Mock
    private SaveUserPort saveUserPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginUseCase = new UserServiceImpl(passwordEncoder, loadUserPort, saveUserPort);
    }

    @DisplayName("로그인 성공")
    @Test
    void testSuccessfulLogin() {
        String username = "messi";
        String rawPassword = "worldcup2022";
        String encodedPassword = "hashedPassword";

        when(loadUserPort.loadByName(username)).thenReturn(Optional.of(
                User.of(1L, username, encodedPassword, "leo", false)));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        UserLoggedInEvent result = loginUseCase.login(new LoginCommand(username, rawPassword));

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(username);
    }

    @DisplayName("로그인 실패")
    @Test
    void testFailedLogin() {
        String username = "messi";
        String rawPassword = "wrongPassword";
        String encodedPassword = "hashedPassword";

        when(loadUserPort.loadByName(username)).thenReturn(Optional.of(
                User.of(1L, "messi", "worldcup2022", "leo", false)));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        assertThatThrownBy(() -> loginUseCase.login(new LoginCommand(username, rawPassword)))
                .isInstanceOf(NotMatchPasswordException.class);
    }
}