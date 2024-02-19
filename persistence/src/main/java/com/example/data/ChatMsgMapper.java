package com.example.data;

import com.example.entity.ChatMsgJPAEntity;
import com.example.model.ChatMsg;

public interface ChatMsgMapper {
    ChatMsgJPAEntity modelToEntity(ChatMsg chatMsg);

    ChatMsg entityToModel(ChatMsgJPAEntity entity);
}
