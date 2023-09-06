package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Entity
public class ChattingJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting")
    private final Long id;
    @Column(name = "message",columnDefinition = "TEXT")
    private final String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private final UserJPAEntity userJPAEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom")
    private final ChatRoomJPAEntity chatroom;
}
