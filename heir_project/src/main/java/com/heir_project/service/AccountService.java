package com.heir_project.service;

import com.heir_project.config.JwtConfig;
import com.heir_project.entity.Accounts;
import com.heir_project.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    @Transactional
    public Accounts signUp(Accounts account) {
        if (account.getUserId() == null || account.getUserId().isBlank()) {
            throw new IllegalArgumentException("ID는 필수 항목입니다.");
        }
        if (accountRepository.findByUserId(account.getUserId()).isPresent()) {
            throw new DataIntegrityViolationException("이미 사용 중인 ID입니다.");
        }

        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException("닉네임은 필수 항목입니다.");
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("이미 존재하는 닉네임입니다.");
        }

        if (account.getPassword() == null || account.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수 항목입니다.");
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public String login(String username, String rawPassword) {
        Accounts acc = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("계정을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(rawPassword, acc.getPassword())) {
            throw new BadCredentialsException("비밀번호가 올바르지 않습니다.");
        }

        return jwtConfig.generateToken(acc.getUsername());
    }

    @Transactional
    public void deleteAccount(String username, String rawPassword) {
        Accounts account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("계정을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(rawPassword, account.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        accountRepository.delete(account); // 연관 엔티티는 cascade로 삭제
    }

    @Transactional
    public String updateUsername(String currentUsername, String newUsername) {
        if (newUsername == null || newUsername.isBlank()) {
            throw new IllegalArgumentException("새 닉네임은 필수 항목입니다.");
        }
        if (accountRepository.findByUsername(newUsername).isPresent()) {
            throw new DataIntegrityViolationException("이미 사용 중인 닉네임입니다.");
        }

        Accounts account = accountRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("계정을 찾을 수 없습니다."));

        account.setUsername(newUsername);
        accountRepository.save(account);
        return newUsername;
    }
}
