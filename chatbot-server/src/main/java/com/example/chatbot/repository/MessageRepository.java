package com.example.chatbot.repository;

import com.example.chatbot.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findTop10ByConversationIdOrderByCreatedAtDesc(Long conversationId);
    List<Message> findAllByConversationIdOrderByCreatedAtAsc(Long conversationId);
    void deleteAllByConversationId(Long conversationId);
    long countByConversationId(Long conversationId);
}
