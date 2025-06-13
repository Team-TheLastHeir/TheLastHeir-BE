package com.example.heir_project.service;

import com.example.heir_project.entity.PlayerQuest;
import com.example.heir_project.entity.Quest;
import com.example.heir_project.entity.QuestStatus;
import com.example.heir_project.entity.PlayerQuestId;
import com.example.heir_project.repository.PlayerQuestRepository;
import com.example.heir_project.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//퀘스트 관련 비즈니스 로직 처리 서비스
@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final PlayerQuestRepository playerQuestRepository;

    public List<Quest> getAllQuests() {
        return questRepository.findAll();
    }

    public List<PlayerQuest> getPlayerQuests(int playerId) {
        return playerQuestRepository.findByPlayerId(playerId);
    }

    public void acceptQuest(int playerId, int questId) {
        PlayerQuest pq = PlayerQuest.builder()
                .playerId(playerId)
                .questId(questId)
                .status(QuestStatus.IN_PROGRESS)
                .build();
        playerQuestRepository.save(pq);
    }

    public void completeQuest(int playerId, int questId) {
        PlayerQuest pq = playerQuestRepository.findById(new PlayerQuestId(playerId, questId))
                .orElseThrow();
        pq.setStatus(QuestStatus.COMPLETED);
        playerQuestRepository.save(pq);
    }
}
