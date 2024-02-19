package com.example.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "chatroom")
@Table(name = "chatroom")
@Data
public class ChatRoomJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "owner")
    private Long owner;

    @OneToMany(mappedBy = "chatroom")
    private List<ChatMsgJPAEntity> chatmsgList;
    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL)
    private List<UserChatRoomJPAEntity> userList;
}