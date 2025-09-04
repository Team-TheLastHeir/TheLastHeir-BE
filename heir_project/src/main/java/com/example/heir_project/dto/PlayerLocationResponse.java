package com.example.heir_project.dto;

import com.example.heir_project.entity.PlayerLocation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerLocationResponse {
    private Integer playerId;
    private String sceneName;
    private Integer campFire;
    private LocalDateTime updatedAt;

    // Entity to DTO
    public static PlayerLocationResponse from(PlayerLocation playerLocation) {
        return PlayerLocationResponse.builder()
                .playerId(playerLocation.getPlayer().getId())
                .sceneName(playerLocation.getSceneName())
                .campFire(playerLocation.getCampFire())
                .updatedAt(playerLocation.getUpdatedAt())
                .build();
    }
}