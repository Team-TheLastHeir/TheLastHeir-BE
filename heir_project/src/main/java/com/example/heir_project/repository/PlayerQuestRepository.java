package com.example.heir_project.repository;

import com.example.heir_project.entity.PlayerQuest;
import com.example.heir_project.entity.PlayerQuestId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerQuestRepository extends JpaRepository<PlayerQuest, PlayerQuestId> {
    List<PlayerQuest> findByPlayerId(int playerId);
}