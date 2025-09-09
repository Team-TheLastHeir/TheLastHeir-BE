package com.heir_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameUpdateRequest {

    @NotBlank(message = "새 닉네임은 필수 항목입니다.")
    private String username;
}
