package com.example.heir_project.service;

import com.example.heir_project.dto.PlayerLocationRequest;
import com.example.heir_project.dto.PlayerLocationResponse;
import com.example.heir_project.entity.PlayerLocation;
import com.example.heir_project.entity.Players;
import com.example.heir_project.exception.exception.PlayerLocationNotFoundException;
import com.example.heir_project.exception.exception.PlayerNotFoundException;
import com.example.heir_project.repository.PlayerLocationRepository;
import com.example.heir_project.repository.PlayersRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerLocationService {

    private final PlayerLocationRepository playerLocationRepository;
    private final PlayersRepository playersRepository;


    // 플레이어 위치 저장 및 업뎃
    @Transactional
    public PlayerLocationResponse saveOrUpdatePlayerLocation(Integer playerId, PlayerLocationRequest requestDto) {
        // 플레이어 존재 여부 확인
        Players player = playersRepository.findById(playerId.longValue())
                .orElseThrow(() -> new PlayerNotFoundException("플레이어를 찾을 수 없음. ID: " + playerId));

        // 기존 위치 정보 조회
        Optional<PlayerLocation> existingLocation = playerLocationRepository.findByPlayerId(playerId);
        PlayerLocation playerLocation;

        if (existingLocation.isPresent()) {
            // 기존 데이터 업데이트
            playerLocation = existingLocation.get();
            playerLocation.setSceneName(requestDto.getSceneName());
            playerLocation.setCampFire(requestDto.getCampFire());
        } else {
            // 새로운 데이터 생성
            playerLocation = PlayerLocation.builder()
                    .player(player)
                    .sceneName(requestDto.getSceneName())
                    .campFire(requestDto.getCampFire())
                    .build();
        }

        PlayerLocation savedLocation = playerLocationRepository.save(playerLocation);
        return PlayerLocationResponse.from(savedLocation);
    }

    // 플레이어 위치 조회
    public PlayerLocationResponse getPlayerLocation(Integer playerId) {
        PlayerLocation playerLocation = playerLocationRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new PlayerLocationNotFoundException("플레이어 위치 정보를 찾을 수 없음. ID: " + playerId));

        return PlayerLocationResponse.from(playerLocation);
    }
}