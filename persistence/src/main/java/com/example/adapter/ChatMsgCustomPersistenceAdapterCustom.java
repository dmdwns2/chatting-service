package com.example.adapter;

import com.example.LoadChatMsgCustomPort;
import com.example.entity.ChatMsgJPAEntity;
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

    @Override
    public List<ChatMsgJPAEntity> findChatroomIdByChatMsg(Long chatMsg, Long lastId) {
        String first = "select c from chatmsg c where c.chatroom.id =: chatmsg order by c.id asc";
        String paging = "select c from chatmsg c where c.chatroom.id =: chatmsg and c.id > :lastId order by c.id asc";

        TypedQuery<ChatMsgJPAEntity> query = null;

        if (Objects.isNull(lastId)) {
            query = entityManager
                    .createQuery(first, ChatMsgJPAEntity.class)
                    .setParameter("chatMsg", chatMsg);
        }
        if (Objects.nonNull(lastId)) {
            query = entityManager
                    .createQuery(paging, ChatMsgJPAEntity.class)
                    .setParameter("chatMsg", chatMsg)
                    .setParameter("lastId", lastId);
        }
        return query
                .setMaxResults(10)
                .getResultList();
    }
}
