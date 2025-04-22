package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.exceptions.CategoryDuplicateException;
import com.uade.tpo.exceptions.CategoryNotFoundException;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.CategoryRequest;
import com.uade.tpo.marketplace.entity.dto.CategoryResponse;
import com.uade.tpo.marketplace.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> responses = categoryService.getCategories()
                .stream()
                .map(categoryService::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId)
                .map(categoryService::mapToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest)
            throws CategoryDuplicateException {
        Category result = categoryService.createCategory(categoryRequest.getDescription());
        CategoryResponse response = categoryService.mapToDto(result);
        return ResponseEntity.created(URI.create("/api/categories/" + result.getId())).body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId,
                                                           @RequestBody @Valid CategoryRequest request) {
        Optional<Category> existingCategory = categoryService.getCategoryById(categoryId);
        if (existingCategory.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        Category category = existingCategory.get();
        category.setDescription(request.getDescription());
        Category updated = categoryService.save(category);
        CategoryResponse response = categoryService.mapToDto(updated);

        return ResponseEntity.ok(response);
    }
}
