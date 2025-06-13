package com.example.heir_project.entity;

import jakarta.persistence.*;
import lombok.*;

//플레이어별 퀘스트 진행 상태를 나타내는 엔티티
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "player_quests")
@IdClass(PlayerQuestId.class)
public class PlayerQuest {

    @Id
    private int playerId;

    @Id
    private int questId;

    @Enumerated(EnumType.STRING)
    private QuestStatus status;
}
