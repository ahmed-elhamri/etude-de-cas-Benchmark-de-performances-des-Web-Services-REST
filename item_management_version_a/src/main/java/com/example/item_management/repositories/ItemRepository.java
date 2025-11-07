package com.example.item_management.repositories;

import com.example.item_management.enitities.Category;
import com.example.item_management.enitities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByCategory(Category category, Pageable pageable);
    Page<Item> findByCategoryId(Long categoryId, Pageable pageable);
}
