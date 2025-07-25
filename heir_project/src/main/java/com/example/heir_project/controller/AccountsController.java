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

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Accounts account) {
        if (accountsRepository.findByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "이미 존재하는 사용자입니다."));
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Accounts saved = accountsRepository.save(account);
        return ResponseEntity.ok(saved);
    }

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
}