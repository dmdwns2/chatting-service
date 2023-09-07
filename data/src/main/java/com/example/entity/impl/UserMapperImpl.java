package com.example.entity.impl;

import com.example.entity.UserJPAEntity;
import com.example.model.User;
import com.example.repository.UserMapper;
import org.springframework.stereotype.Component;

@Component
class UserMapperImpl implements UserMapper {
    @Override
    public UserJPAEntity modelToEntity(User user) {
        UserJPAEntity entity = new UserJPAEntity();
        entity.setName(user.getName());
        entity.setPassword(user.getPassword());
        entity.setNickname(user.getNickname());
        return entity;
    }

    @Override
    public User EntityToModel(UserJPAEntity entity) {
        return User.of(entity.getName(), entity.getPassword(), entity.getNickname());
    }
}
