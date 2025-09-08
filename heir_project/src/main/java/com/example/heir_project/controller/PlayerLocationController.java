package com.example.heir_project.controller;

import com.example.heir_project.dto.PlayerLocationRequest;
import com.example.heir_project.dto.PlayerLocationResponse;
import com.example.heir_project.service.PlayerLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player-location")
@RequiredArgsConstructor
public class PlayerLocationController {

    private final PlayerLocationService playerLocationService;


    // 플레이어 위치 저장
    // POST /api/player-location/{playerId}
    @PostMapping("/{playerId}")
    public ResponseEntity<PlayerLocationResponse> savePlayerLocation(
            @PathVariable Integer playerId,
            @RequestBody PlayerLocationRequest requestDto) {

        try {
            PlayerLocationResponse response = playerLocationService.saveOrUpdatePlayerLocation(playerId, requestDto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 플레이어 위치 업뎃
    // PUT /api/player-location/{playerId}
    @PutMapping("/{playerId}")
    public ResponseEntity<PlayerLocationResponse> updatePlayerLocation(
            @PathVariable Integer playerId,
            @RequestBody PlayerLocationRequest requestDto) {

        try {
            PlayerLocationResponse response = playerLocationService.saveOrUpdatePlayerLocation(playerId, requestDto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 플레이어 위치 조회
    // GET /api/player-location/{playerId}
    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerLocationResponse> getPlayerLocation(@PathVariable Integer playerId) {
        try {
            PlayerLocationResponse response = playerLocationService.getPlayerLocation(playerId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}