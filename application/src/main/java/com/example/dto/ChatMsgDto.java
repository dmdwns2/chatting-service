package com.example.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMsgDto {
    private Long chatMsgId;
    private Long userId;
    private String nickname;
    private String message;
    private LocalDateTime sendTime;
}

