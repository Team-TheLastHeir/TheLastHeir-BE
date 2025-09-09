package com.heir_project.repository;

import com.heir_project.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Accounts, Long> {
    Optional<Accounts> findByUsername(String username);
    Optional<Accounts> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
