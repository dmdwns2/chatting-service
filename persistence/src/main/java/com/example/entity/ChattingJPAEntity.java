package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "chatting")
@Table(name = "chatting")
@Data
public class ChattingJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_id")
    private Long id;
    @Column(name = "message",columnDefinition = "TEXT")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJPAEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoomJPAEntity chatroom;
}
