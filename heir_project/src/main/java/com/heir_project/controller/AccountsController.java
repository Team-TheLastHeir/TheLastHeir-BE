package com.heir_project.controller;

import com.heir_project.dto.AccountDeleteRequest;
import com.heir_project.dto.LoginRequest;
import com.heir_project.dto.UsernameUpdateRequest;
import com.heir_project.entity.Accounts;
import com.heir_project.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountsController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Accounts account) {
        Accounts saved = accountService.signUp(account);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "회원가입이 완료되었습니다.",
                "user", saved
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String token = accountService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token
        ));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestBody AccountDeleteRequest request,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        accountService.deleteAccount(userDetails.getUsername(), request.getPassword());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "계정이 성공적으로 삭제되었습니다."
        ));
    }

    @PatchMapping("/username")
    public ResponseEntity<?> updateUsername(@Valid @RequestBody UsernameUpdateRequest body,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        String updated = accountService.updateUsername(userDetails.getUsername(), body.getUsername());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "닉네임이 성공적으로 변경되었습니다.",
                "newUsername", updated
        ));
    }
}
