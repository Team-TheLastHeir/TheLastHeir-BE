package com.example.heir_project.repository;

import com.example.heir_project.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayersRepository extends JpaRepository<Players, Integer> {
}