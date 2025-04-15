package com.uade.tpo.marketplace.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.exceptions.CategoryDuplicateException;
import com.uade.tpo.marketplace.entity.Category;



public interface CategoryService {
    public List<Category> getCategories(); // ya no usa PageRequest

    public Optional<Category> getCategoryById(Long categoryId);

    public Category createCategory(String description) throws CategoryDuplicateException;

    public void deleteCategoryById(Long id);

    public Category save(Category category);
}