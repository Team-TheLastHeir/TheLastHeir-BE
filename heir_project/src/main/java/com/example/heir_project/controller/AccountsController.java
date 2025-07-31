package com.example.heir_project.controller;

import com.example.heir_project.config.JwtConfig;
import com.example.heir_project.dto.AccountDeleteRequest;
import com.example.heir_project.entity.Accounts;
import com.example.heir_project.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountsController {

    private final AccountRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    // 회원가입
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Accounts account) {
        if (account.getUserId() == null || account.getUserId().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "ID는 필수 항목입니다."));
        }
        if (accountsRepository.findByUserId(account.getUserId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "이미 사용 중인 ID입니다."));
        }

        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "닉네임은 필수 항목입니다."));
        }
        if (accountsRepository.findByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "이미 존재하는 닉네임입니다."));
        }

        if (account.getPassword() == null || account.getPassword().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "비밀번호는 필수 항목입니다."));
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Accounts saved = accountsRepository.save(account);
        return ResponseEntity.ok(Map.of("success", true, "message", "회원가입이 완료되었습니다.", "user", saved));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Accounts request) {
        Optional<Accounts> optionalAccount = accountsRepository.findByUsername(request.getUsername());

        if (optionalAccount.isPresent()) {
            Accounts acc = optionalAccount.get();
            if (passwordEncoder.matches(request.getPassword(), acc.getPassword())) {
                String token = jwtConfig.generateToken(acc.getUsername());
                return ResponseEntity.ok(Map.of("success", true, "token", token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("success", false, "message", "비밀번호가 올바르지 않습니다."));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("success", false, "message", "계정을 찾을 수 없습니다."));
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestBody AccountDeleteRequest request,
                                           @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Accounts> optionalAccount = accountsRepository.findByUsername(userDetails.getUsername());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "계정을 찾을 수 없습니다."));
        }

        Accounts account = optionalAccount.get();

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "비밀번호가 일치하지 않습니다."));
        }

        accountsRepository.delete(account);
        return ResponseEntity.ok(Map.of("success", true, "message", "계정이 성공적으로 삭제되었습니다."));
    }

    // 닉네임 수정
    @PatchMapping("/username")
    public ResponseEntity<?> updateUsername(@RequestBody Map<String, String> body,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        String newUsername = body.get("username");

        if (newUsername == null || newUsername.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "새 닉네임은 필수 항목입니다."));
        }

        // 중복 닉네임 체크
        if (accountsRepository.findByUsername(newUsername).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "이미 사용 중인 닉네임입니다."));
        }

        // 현재 사용자 조회
        Optional<Accounts> optionalAccount = accountsRepository.findByUsername(userDetails.getUsername());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "계정을 찾을 수 없습니다."));
        }

        // 닉네임 변경 및 저장
        Accounts account = optionalAccount.get();
        account.setUsername(newUsername);
        accountsRepository.save(account);

        return ResponseEntity.ok(Map.of("success", true, "message", "닉네임이 성공적으로 변경되었습니다.", "newUsername", newUsername));
    }
}
