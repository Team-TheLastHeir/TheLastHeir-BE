package com.example.heir_project.service;

import com.example.heir_project.entity.Players;
import com.example.heir_project.repository.PlayersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayersRepository playersRepository;

    public List<Players> getAllPlayers() {
        return playersRepository.findAll();
    }
}
