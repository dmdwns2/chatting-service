package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Entity
public class UserJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user")
    private final Long id;
    @Column(name = "name")
    private final String name;
    @Column(name = "password")
    private final String password;
    @Column(name = "nickname")
    private final String nickname;

    @OneToMany(mappedBy = "user")
    private final List<ChattingJPAEntity> chattingJPAEntityList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<UserChatRoomJPAEntity> chatroomList;
}
