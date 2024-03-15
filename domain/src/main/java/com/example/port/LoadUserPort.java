package com.example.port;

import com.example.model.User;

import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadById(Long userId);

    Optional<User> loadByName(String name);
}