package com.example.usecase.impl;

import com.example.LoginServiceImpl;
import com.example.dto.LoginCommand;
import com.example.dto.UserLoggedInEvent;
import com.example.entity.UserJPAEntity;
import com.example.exception.NotMatchPasswordException;
import com.example.jwt.TokenProvider;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class LoginServiceImplTest {

    private LoginServiceImpl loginUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginUseCase = new LoginServiceImpl(userRepository, passwordEncoder, tokenProvider);
    }

    @DisplayName("로그인 성공")
    @Test
    void testSuccessfulLogin() {
        String username = "testUser";
        String rawPassword = "testPassword";
        String encodedPassword = "hashedPassword";

        UserJPAEntity user = new UserJPAEntity();
        user.setName(username);
        user.setPassword(encodedPassword);

        when(userRepository.findByName(username)).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        UserLoggedInEvent result = loginUseCase.login(new LoginCommand(username, rawPassword));

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(username);
    }

    @DisplayName("로그인 실패")
    @Test
    void testFailedLogin() {
        String username = "testUser";
        String rawPassword = "wrongPassword";
        String encodedPassword = "hashedPassword";

        UserJPAEntity user = new UserJPAEntity();
        user.setName(username);
        user.setPassword(encodedPassword);

        when(userRepository.findByName(username)).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        assertThatThrownBy(() -> loginUseCase.login(new LoginCommand(username, rawPassword)))
                .isInstanceOf(NotMatchPasswordException.class);
    }
}