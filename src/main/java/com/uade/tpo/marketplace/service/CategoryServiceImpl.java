package com.uade.tpo.marketplace.service;

import com.uade.tpo.exceptions.CategoryDuplicateException;
import com.uade.tpo.exceptions.CategoryHasProductsException;
import com.uade.tpo.exceptions.CategoryNotFoundException;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.CategoryResponse;
import com.uade.tpo.marketplace.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createCategory(String description) throws CategoryDuplicateException {
        List<Category> categories = categoryRepository.findByDescription(description);
        if (categories.isEmpty()) {
            return categoryRepository.save(new Category(description));
        }
        throw new CategoryDuplicateException();
    }

    @Override
    public void deleteCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        Category category = optionalCategory.get();

        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new CategoryHasProductsException();
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public CategoryResponse mapToDto(Category category) {
        return new CategoryResponse(category.getId(), category.getDescription());
    }
}
