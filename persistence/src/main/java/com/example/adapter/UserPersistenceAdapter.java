package com.example.adapter;

import com.example.data.UserMapper;
import com.example.model.User;
import com.example.port.*;
import com.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
class UserPersistenceAdapter implements ExistsNamePort, ExistsNicknamePort, SaveUserPort, LoadUserPort, GetUserPort {

    private final UserRepository userRepository;
    private final UserMapper  userMapper;

    UserPersistenceAdapter(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean existsName(String name) {
        return userRepository.findByName(name).isPresent();
    }

    @Override
    public boolean existsNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    @Override
    public void save(User user) {
        userRepository.save(userMapper.modelToEntity(user));
    }

    @Override
    public Optional<User> load(String name) {
        return userRepository.findByName(name).map(userMapper::entityToModel);
    }

    @Override
    public Page<User> list(String query, int page) {
        return null;
    }

    @Override
    public Set<User> listByName(Set<String> name) {
        return null;
    }
}
