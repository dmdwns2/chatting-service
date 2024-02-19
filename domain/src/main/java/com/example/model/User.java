package com.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class User {
    private final Long id;
    private final String name;
    private final String password;
    private final String nickname;
    private final boolean isLogin;

    public static User of(Long id, String name, String password, String nickname, Boolean isLogin) {
        return new User(id, name, password, nickname, isLogin);
    }
}