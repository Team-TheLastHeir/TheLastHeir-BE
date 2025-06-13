package com.example.heir_project.controller;

import com.example.heir_project.entity.PlayerQuest;
import com.example.heir_project.entity.Quest;
import com.example.heir_project.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//퀘스트 관련 API를 제공하는 컨트롤러
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quests")
public class QuestController {

    private final QuestService questService;

    // 전체 퀘스트 목록 조회
    @GetMapping
    public List<Quest> getAllQuests() {
        return questService.getAllQuests();
    }

    // 플레이어의 퀘스트 상태 목록 조회
    @GetMapping("/player/{playerId}")
    public List<PlayerQuest> getPlayerQuests(@PathVariable int playerId) {
        return questService.getPlayerQuests(playerId);
    }

    // 퀘스트 수락
    @PostMapping("/player/{playerId}/accept/{questId}")
    public void acceptQuest(@PathVariable int playerId, @PathVariable int questId) {
        questService.acceptQuest(playerId, questId);
    }

    // 퀘스트 완료 처리
    @PatchMapping("/player/{playerId}/complete/{questId}")
    public void completeQuest(@PathVariable int playerId, @PathVariable int questId) {
        questService.completeQuest(playerId, questId);
    }
}
