package com.example.heir_project.controller;

import com.example.heir_project.dto.AccountDeleteRequest;
import com.example.heir_project.entity.Accounts;
import com.example.heir_project.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountsController {

    private final AccountRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Accounts> createAccount(@RequestBody Accounts account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return ResponseEntity.ok(accountsRepository.save(account));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Accounts request) {
        Optional<Accounts> optionalAccount = accountsRepository.findByUsername(request.getUsername());
        if (optionalAccount.isPresent()) {
            Accounts acc = optionalAccount.get();
            if (passwordEncoder.matches(request.getPassword(), acc.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        }
        return ResponseEntity.status(404).body("Account not found");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAccount(@RequestBody AccountDeleteRequest request,
                                                @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Accounts> optionalAccount = accountsRepository.findByUsername(userDetails.getUsername());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.status(404).body("Account not found");
        }

        Accounts account = optionalAccount.get();

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        accountsRepository.delete(account); // Cascade 삭제 설정 필요
        return ResponseEntity.ok("계정이 성공적으로 삭제되었습니다.");
    }
}