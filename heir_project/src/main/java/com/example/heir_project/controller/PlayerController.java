package com.example.heir_project.controller;

import com.example.heir_project.entity.Players;
import com.example.heir_project.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public List<Players> getAllPlayers() {
        return playerService.getAllPlayers();
    }
}