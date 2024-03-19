package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "chatmsg")
@Table(name = "chatmsg")
@Data
public class ChatMsgJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatmsg_id")
    private Long id;
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    @Column(name = "send_time", columnDefinition = "DATETIME")
    private LocalDateTime sendTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJPAEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoomJPAEntity chatroom;
}