package com.example.item_management.controllers;

import com.example.item_management.enitities.Item;
import com.example.item_management.repositories.ItemRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/management")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // READ: Récupérer tous les items (JSON et XML)
    @GetMapping(value = "/items", produces = { "application/json", "application/xml" })
    public Page<Item> getItems(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Item> result;
        if (categoryId != null) {
            System.out.println("Searching for categoryId: " + categoryId);
            result = itemRepository.findByCategoryId(categoryId, pageable);
            System.out.println("Total elements found: " + result.getTotalElements());
            System.out.println("Content size: " + result.getContent().size());
            System.out.println("Items: " + result.getContent());
        } else {
            result = itemRepository.findAll(pageable);
        }

        return result;
    }

    @GetMapping(value = "/categories/{categoryId}/items", produces = { "application/json", "application/xml" })
    public Page<Item> getItemsByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        System.out.println("Fetching items for category: " + categoryId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> result = itemRepository.findByCategoryId(categoryId, pageable);
        System.out.println("Found " + result.getTotalElements() + " items");
        return result;
    }

    // READ: Récupérer un item par son identifiant (JSON et XML)
    @GetMapping(value = "/items/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .map(item -> ResponseEntity.ok().body(item))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE: Ajouter un nouveau item (JSON et XML)
    @PostMapping(value = "/items", consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    // UPDATE: Mettre à jour un item existant (JSON et XML)
    @PutMapping(value = "/items/{id}", consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setSku(itemDetails.getSku());
                    item.setName(itemDetails.getName());
                    item.setPrice(itemDetails.getPrice());
                    item.setStock(itemDetails.getStock());
                    item.setCategory(itemDetails.getCategory());
                    item.setUpdatedAt(LocalDateTime.now());
                    Item updatedItem = itemRepository.save(item);
                    return ResponseEntity.ok().body(updatedItem);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Supprimer un item
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        return itemRepository.findById(id)
                .map(item -> {
                    itemRepository.delete(item);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
