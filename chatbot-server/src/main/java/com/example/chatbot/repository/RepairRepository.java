package com.example.chatbot.repository;

import com.example.chatbot.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairRepository extends JpaRepository<Repair, Long> {
    List<Repair> findByUserId(Long id);
}
