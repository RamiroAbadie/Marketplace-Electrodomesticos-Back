package com.uade.tpo.marketplace.controllers.categories;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.exceptions.CategoryDuplicateException;
import com.uade.tpo.exceptions.CategoryNotFoundException;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.CategoryRequest;
import com.uade.tpo.marketplace.entity.dto.CategoryResponse;
import com.uade.tpo.marketplace.service.CategoryService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    // Ver todas las categorias
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        List<CategoryResponse> responses = new ArrayList<>();
    
        for (Category c : categories) {
            responses.add(new CategoryResponse(c.getId(), c.getDescription()));
        }
    
        return ResponseEntity.ok(responses);
    }
    

    // Ver cateogria por su id
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
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest)
            throws CategoryDuplicateException {
        Category result = categoryService.createCategory(categoryRequest.getDescription());
        CategoryResponse response = new CategoryResponse(result.getId(), result.getDescription());
        return ResponseEntity.created(URI.create("/api/categories/" + result.getId())).body(response);
    }

    // Borrar categoria por id
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Actualizar el nombre de una categoria
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId, 
                                                @RequestBody @Valid CategoryRequest request) {
        Optional<Category> existingCategory = categoryService.getCategoryById(categoryId);
        if (existingCategory.isEmpty()) {
            throw new CategoryNotFoundException(); // 404 Not Found
        }
        Category category = existingCategory.get();
        category.setDescription(request.getDescription());
        Category updated = categoryService.save(category);
        CategoryResponse response = new CategoryResponse(updated.getId(), updated.getDescription());
        return ResponseEntity.ok(response);
    }

}
