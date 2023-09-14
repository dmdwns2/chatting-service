package com.example.entity;

import jakarta.persistence.*;

import lombok.Data;

@Entity(name = "user_chatroom")
@Table(name = "user_chatroom")
@Data
public class UserChatRoomJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_chatroom_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJPAEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoomJPAEntity chatroom;
}