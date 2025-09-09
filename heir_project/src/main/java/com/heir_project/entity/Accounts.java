package com.heir_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB용 PK (자동 증가)

    @Column(nullable = false, unique = true)
    private String userId; // 사용자가 입력하는 ID (로그인용 ID)

    @Column(nullable = false, unique = true)
    private String username; // 닉네임 (표시용)

    @Column(nullable = false)
    private String password;

    private String email;
}
