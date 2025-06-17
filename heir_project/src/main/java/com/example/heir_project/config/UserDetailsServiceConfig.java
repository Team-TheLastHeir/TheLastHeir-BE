package com.example.heir_project.config;

import com.example.heir_project.entity.Accounts;
import com.example.heir_project.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfig {

    private final AccountRepository accountRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Accounts account = accountRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
            return User.builder()
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .roles("USER")
                    .build();
        };
    }
}
