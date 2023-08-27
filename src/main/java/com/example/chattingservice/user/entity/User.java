package com.example.chattingservice.user.entity;

import com.example.chattingservice.chatting.entity.Chatting;
import com.example.chattingservice.common.entity.BaseEntity;
import com.example.chattingservice.userchatroom.entity.UserChatRoom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Entity
public class User extends BaseEntity {

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
    private final List<Chatting> chattingList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<UserChatRoom> chatroomList;
}
