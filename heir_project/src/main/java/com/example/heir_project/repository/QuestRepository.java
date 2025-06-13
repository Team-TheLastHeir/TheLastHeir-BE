package com.example.heir_project.repository;

import com.example.heir_project.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Integer> {
}
