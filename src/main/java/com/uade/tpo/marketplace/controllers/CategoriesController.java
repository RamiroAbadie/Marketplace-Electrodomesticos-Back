package com.uade.tpo.marketplace.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.exceptions.CategoryDuplicateException;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.CategoryRequest;
import com.uade.tpo.marketplace.entity.dto.CategoryResponse;
import com.uade.tpo.marketplace.service.CategoryService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    //Ver todas las categorias
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        List<CategoryResponse> responses = new ArrayList<>();
    
        for (Category c : categories) {
            responses.add(new CategoryResponse(c.getId(), c.getDescription()));
        }
    
        return ResponseEntity.ok(responses);
    }
    


    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        Optional<Category> result = categoryService.getCategoryById(categoryId);
        if (result.isPresent()) {
            Category category = result.get();
            return ResponseEntity.ok(new CategoryResponse(category.getId(), category.getDescription()));
        }
        return ResponseEntity.noContent().build();
    }
    

    //Crear categoria
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest)
            throws CategoryDuplicateException {

        Category result = categoryService.createCategory(categoryRequest.getDescription());

        CategoryResponse response = new CategoryResponse(result.getId(), result.getDescription());

        return ResponseEntity.created(URI.create("/api/categories/" + result.getId())).body(response);
    }

}
