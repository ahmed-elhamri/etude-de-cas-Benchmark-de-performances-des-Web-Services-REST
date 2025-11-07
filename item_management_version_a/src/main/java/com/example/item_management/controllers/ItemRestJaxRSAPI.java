package com.example.item_management.controllers;

import com.example.item_management.enitities.Category;
import com.example.item_management.enitities.Item;
import com.example.item_management.repositories.ItemRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Path("/management")
public class ItemRestJaxRSAPI {

    @Autowired
    private ItemRepository itemRepository;

    // READ: Récupérer tous les Items (JSON et XML)
    @Path("/items")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Page<Item> getItems(
            @QueryParam("categoryId") Long categoryId,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (categoryId != null) {
            return itemRepository.findByCategoryId(categoryId, pageable);
        }

        return itemRepository.findAll(pageable);
    }

    // READ: Récupérer un item par son identifiant (JSON et XML)
    @Path("/items/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Item getItem(@PathParam("id") Long id) {
        return itemRepository.findById(id).orElse(null);
    }


    @Path("/categories/{categoryId}/items")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Page<Item> getItemsByCategory(
            @PathParam("categoryId") Long categoryId,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepository.findByCategoryId(categoryId, pageable);
    }
    // CREATE: Ajouter un nouveau item (JSON et XML)
    @Path("/items")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    // UPDATE: Mettre à jour un item existant (JSON et XML)
    @Path("/items/{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Item updateItem(@PathParam("id") Long id, Item item) {
        Item existingItem = itemRepository.findById(id).orElse(null);
        if (existingItem != null) {
            existingItem.setSku(item.getSku());
            existingItem.setName(item.getName());
            existingItem.setPrice(item.getPrice());
            existingItem.setStock(item.getStock());
            existingItem.setCategory(item.getCategory());
            existingItem.setUpdatedAt(LocalDateTime.now());
            return itemRepository.save(existingItem);
        }
        return null;
    }

    // DELETE: Supprimer un item (JSON et XML)
    @Path("/items/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteItem(@PathParam("id") Long id) {
        itemRepository.deleteById(id);
    }
}