package com.example.heir_project.controller;

import com.example.heir_project.entity.Accounts;
import com.example.heir_project.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsRepository accountsRepository;
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
}