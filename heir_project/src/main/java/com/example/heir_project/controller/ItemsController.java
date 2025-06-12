package com.example.heir_project.controller;

import com.example.heir_project.entity.Items;
import com.example.heir_project.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemsController {
    private final ItemsRepository itemsRepository;

    @GetMapping
    public List<Items> getAllItems() {
        return itemsRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Items> addItem(@RequestBody Items item) {
        return ResponseEntity.ok(itemsRepository.save(item));
    }
}