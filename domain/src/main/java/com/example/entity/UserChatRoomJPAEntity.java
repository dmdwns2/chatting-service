package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Entity
public class UserChatRoomJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_chatting")
    private final Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private final UserJPAEntity userJPAEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom")
    private final ChatRoomJPAEntity chatroom;

}