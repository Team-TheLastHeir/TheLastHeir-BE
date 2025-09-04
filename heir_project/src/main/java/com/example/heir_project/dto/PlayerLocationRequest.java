package com.example.heir_project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerLocationRequest {
    private String sceneName;
    private Integer campFire;
}