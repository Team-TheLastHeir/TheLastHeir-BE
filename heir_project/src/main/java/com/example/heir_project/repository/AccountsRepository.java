package com.example.heir_project.repository;

import com.example.heir_project.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
    Optional<Accounts> findByUsername(String username);
}