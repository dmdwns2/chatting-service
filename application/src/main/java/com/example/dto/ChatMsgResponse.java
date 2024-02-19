package com.example.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMsgResponse {
    private Long owner;
    private String from; // 사용자, user
    private String message;
    private LocalDateTime sendTime;
}

