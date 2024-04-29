package com.example.data.impl;

import com.example.data.UserMapper;
import com.example.entity.UserJPAEntity;
import com.example.model.User;
import org.springframework.stereotype.Component;

@Component
class UserMapperImpl implements UserMapper {
    @Override
    public UserJPAEntity modelToEntity(final User user) {
        UserJPAEntity entity = new UserJPAEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setPassword(user.getPassword());
        entity.setNickname(user.getNickname());
        entity.setIsLogin(user.isLogin());
        return entity;
    }

    @Override
    public User entityToModel(final UserJPAEntity entity) {
        return User.of(
                entity.getId(), entity.getName(), entity.getPassword(), entity.getNickname(), entity.getIsLogin());
    }
}