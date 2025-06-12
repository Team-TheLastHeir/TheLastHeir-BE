package com.example.heir_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "players")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Players {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Accounts account;

    private String nickname;
    private Integer level;
    private Integer exp;
    private Integer str;
    private Integer dex;

    @Column(name = "int_stat")
    private Integer intStat;

    private String location;
}