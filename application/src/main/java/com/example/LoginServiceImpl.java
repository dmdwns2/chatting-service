package com.example;

import com.example.dto.LoginCommand;
import com.example.dto.UserLoggedInEvent;
import com.example.entity.UserJPAEntity;
import com.example.exception.NotFoundUserException;
import com.example.exception.NotMatchPasswordException;
import com.example.jwt.TokenProvider;
import com.example.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public UserLoggedInEvent login(LoginCommand command) {
        validateCommand(command);
        UserJPAEntity user = userRepository.findByName(command.getName())
                .orElseThrow(() -> new NotFoundUserException(command.getName()));
        validatePasswordMatching(command.getPassword(), user.getPassword());

        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getName(),
                user.getPassword(),
                Collections.singletonList(userAuthority)
        );
        String authToken = tokenProvider.createToken(authentication);

        System.out.println("token : " + authToken);
        return new UserLoggedInEvent(user.getName(), authToken);
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