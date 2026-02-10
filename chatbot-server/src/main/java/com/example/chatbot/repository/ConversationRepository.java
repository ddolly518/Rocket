package com.example.chatbot.repository;

import com.example.chatbot.entity.Conversation;
import com.example.chatbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findAllByUserOrderByCreatedAtDesc(User user);
}
