package com.example.heir_project.repository;

import com.example.heir_project.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Accounts, String> {
    Optional<Accounts> findByUsername(String username);

    Optional<Accounts> findByUserId(String userId);
    boolean existsByUserId(String userId);
}