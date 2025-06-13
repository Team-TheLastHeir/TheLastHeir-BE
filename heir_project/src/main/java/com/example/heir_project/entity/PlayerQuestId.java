package com.example.heir_project.entity;

import lombok.*;

import java.io.Serializable;

// PlayerQuest테이블의 복합키 정의 클래스
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlayerQuestId implements Serializable {
    private int playerId;
    private int questId;
}
