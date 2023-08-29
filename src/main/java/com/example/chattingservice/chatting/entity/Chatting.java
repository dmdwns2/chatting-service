package com.example.chattingservice.chatting.entity;

import com.example.chattingservice.chatroom.entity.ChatRoom;
import com.example.chattingservice.common.entity.BaseEntity;
import com.example.chattingservice.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Entity
public class Chatting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting")
    private final Long id;
    @Column(name = "message",columnDefinition = "TEXT")
    private final String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private final User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom")
    private final ChatRoom chatroom;
}
