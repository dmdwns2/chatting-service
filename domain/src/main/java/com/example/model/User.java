package com.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {
    private final String name;
    private final String password;
    private final String nickname;

    public static User of(String name, String password, String nickname){
        return new User(name, password, nickname);
    }
}
