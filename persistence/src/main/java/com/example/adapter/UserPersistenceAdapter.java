package com.example.adapter;

import com.example.data.UserMapper;
import com.example.model.User;
import com.example.port.*;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements
        ExistsNamePort, ExistsUserPort, ExistsNicknamePort, SaveUserPort, LoadUserPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean existsUser(final Long userId) {
        return userRepository.findById(userId).isPresent();
    }

    @Override
    public boolean existsName(final String name) {
        return userRepository.findByName(name).isPresent();
    }

    @Override
    public boolean existsNickname(final String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    @Override
    public void save(final User user) {
        userRepository.save(userMapper.modelToEntity(user));
    }

    @Override
    public Optional<User> loadById(final Long userId) {
        return userRepository.findById(userId).map(userMapper::entityToModel);
    }

    @Override
    public Optional<User> loadByName(final String name) {
        return userRepository.findByName(name).map(userMapper::entityToModel);
    }
}