package com.example.item_management.controllers;

import com.example.item_management.enitities.Category;
import com.example.item_management.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/management")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // READ: Récupérer tous les categories (JSON et XML)
    @GetMapping(value = "/categories", produces = { "application/json", "application/xml" })
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }

    // READ: Récupérer un category par son identifiant (JSON et XML)
    @GetMapping(value = "/categories/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(category -> ResponseEntity.ok().body(category))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE: Ajouter un nouveau category (JSON et XML)
    @PostMapping(value = "/categories", consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // UPDATE: Mettre à jour un category existant (JSON et XML)
    @PutMapping(value = "/categories/{id}", consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setCode(categoryDetails.getCode());
                    category.setName(categoryDetails.getName());
                    category.setUpdatedAt(LocalDateTime.now());
                    Category updatedCategory = categoryRepository.save(category);
                    return ResponseEntity.ok().body(updatedCategory);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Supprimer un category
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
