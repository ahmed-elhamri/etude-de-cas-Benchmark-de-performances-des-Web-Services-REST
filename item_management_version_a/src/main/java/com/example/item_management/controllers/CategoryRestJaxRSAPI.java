package com.example.item_management.controllers;

import com.example.item_management.enitities.Category;
import com.example.item_management.enitities.Item;
import com.example.item_management.repositories.CategoryRepository;
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
public class CategoryRestJaxRSAPI {

    @Autowired
    private CategoryRepository categoryRepository;

    // READ: Récupérer tous les categories (JSON et XML)
    @Path("/categories")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Page<Category> getItems(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable);
    }

    // READ: Récupérer un category par son identifiant (JSON et XML)
    @Path("/categories/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Category getCategory(@PathParam("id") Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // CREATE: Ajouter un nouveau category (JSON et XML)
    @Path("/categories")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    // UPDATE: Mettre à jour un category existant (JSON et XML)
    @Path("/categories/{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Category updateCategory(@PathParam("id") Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setCode(category.getCode());
            existingCategory.setName(category.getName());
            existingCategory.setUpdatedAt(LocalDateTime.now());
            return categoryRepository.save(existingCategory);
        }
        return null;
    }

    // DELETE: Supprimer un category (JSON et XML)
    @Path("/categories/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteCategory(@PathParam("id") Long id) {
        categoryRepository.deleteById(id);
    }
}