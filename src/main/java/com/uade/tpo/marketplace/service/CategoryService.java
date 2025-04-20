package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.CategoryResponse;
import com.uade.tpo.exceptions.CategoryDuplicateException;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();

    Optional<Category> getCategoryById(Long categoryId);

    Category createCategory(String description) throws CategoryDuplicateException;

    void deleteCategoryById(Long id);

    Category save(Category category);

    CategoryResponse mapToDto(Category category);
}
