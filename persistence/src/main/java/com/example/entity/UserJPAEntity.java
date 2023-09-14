package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "user")
@Table(name = "user")
@Data
public class UserJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<ChattingJPAEntity> chatting;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserChatRoomJPAEntity> chatroomList;
}
