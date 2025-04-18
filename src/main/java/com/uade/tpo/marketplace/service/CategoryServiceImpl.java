package com.uade.tpo.marketplace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.exceptions.CategoryDuplicateException;
import com.uade.tpo.exceptions.CategoryHasProductsException;
import com.uade.tpo.exceptions.CategoryNotFoundException;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Ver todas las categorias
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }    

    // Ver cateogria por id
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    // Crear nueva categoria
    public Category createCategory(String description) throws CategoryDuplicateException {
        List<Category> categories = categoryRepository.findByDescription(description);
        if (categories.isEmpty())
            return categoryRepository.save(new Category(description));
        throw new CategoryDuplicateException(); // 400 Bad Request
    }

    // Borrar categoria por id
    @Override
    public void deleteCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(); // 404 Not Found
        }

        // A partir de acá, estoy seguro de que la categoría existe, ya valide el Optional
        Category category = optionalCategory.get();

        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new CategoryHasProductsException(); // 409 Conflict
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public Category save(Category category) {
        // Estamos usando un metodo que ya viene implementado por Spring Data JPA
        return categoryRepository.save(category);
    }
}
