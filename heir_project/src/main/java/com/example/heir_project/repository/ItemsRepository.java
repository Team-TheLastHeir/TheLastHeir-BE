package com.example.heir_project.repository;

import com.example.heir_project.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Integer> {
}