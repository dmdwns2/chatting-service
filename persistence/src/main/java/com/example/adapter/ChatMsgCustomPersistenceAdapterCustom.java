package com.example.adapter;

import com.example.LoadChatMsgCustomPort;
import com.example.data.ChatMsgMapper;
import com.example.entity.ChatMsgJPAEntity;
import com.example.model.ChatMsg;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class ChatMsgCustomPersistenceAdapterCustom implements LoadChatMsgCustomPort {
    private final EntityManager entityManager;
    private final ChatMsgMapper chatMsgMapper;

    @Override
    public List<ChatMsg> findChatroomIdByChatMsg(Long chatmsg, Long lastId) {
        String first = "select c from chatmsg c where c.chatroom.id =: chatmsg order by c.id asc";
        String paging = "select c from chatmsg c where c.chatroom.id =: chatmsg and c.id > :lastId order by c.id asc";

        TypedQuery<ChatMsgJPAEntity> query = null;

        if (Objects.isNull(lastId)) {
            query = entityManager
                    .createQuery(first, ChatMsgJPAEntity.class)
                    .setParameter("chatmsg", chatmsg);
        }
        if (Objects.nonNull(lastId)) {
            query = entityManager
                    .createQuery(paging, ChatMsgJPAEntity.class)
                    .setParameter("chatmsg", chatmsg)
                    .setParameter("lastId", lastId);
        }
        return query
                .setMaxResults(10)
                .getResultList()
                .stream()
                .map(chatMsgMapper::entityToModel)
                .toList();
    }
}
