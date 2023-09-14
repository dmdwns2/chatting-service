package com.example.repository;

import com.example.entity.UserJPAEntity;
import com.example.model.User;

public interface UserMapper {
    User entityToModel(UserJPAEntity userJPAEntity);
    UserJPAEntity modelToEntity(User user);
}

