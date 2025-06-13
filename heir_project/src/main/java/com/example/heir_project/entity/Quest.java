package com.example.heir_project.entity;

import jakarta.persistence.*;
import lombok.*;

// 퀘스트 정의 정보 엔티티
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "quests")
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_main")
    private boolean isMain;
}
