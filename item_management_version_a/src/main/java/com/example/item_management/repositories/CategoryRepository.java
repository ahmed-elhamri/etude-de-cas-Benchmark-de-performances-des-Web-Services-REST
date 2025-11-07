package com.example.item_management.repositories;

import com.example.item_management.enitities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
