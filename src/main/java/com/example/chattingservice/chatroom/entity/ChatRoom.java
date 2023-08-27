package com.example.chattingservice.chatroom.entity;

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
public class ChatRoom extends BaseEntity {

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
