package com.example.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom")
    private final Long id;
    @Column(name = "title")
    private final String title;
    @Column(name = "owner")
    private final String owner;

    @OneToMany(mappedBy = "chatroom")
    private final List<Chatting> chattingList;
    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL)
    private final List<UserChatRoom> userList;
}
