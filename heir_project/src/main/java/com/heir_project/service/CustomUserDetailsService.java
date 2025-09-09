package com.heir_project.service;

import com.heir_project.entity.Accounts;
import com.heir_project.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Accounts account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다: " + username));

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles("USER")
                .authorities(Collections.emptyList()) // 권한이 없을 경우 비워놓음
                .build();
    }
}
