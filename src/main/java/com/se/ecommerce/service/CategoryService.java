package com.se.ecommerce.service;

import com.se.ecommerce.dto.category.CategoryDto;
import com.se.ecommerce.model.Category;
import com.se.ecommerce.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public boolean categoryExists(String name) {
        Optional<Category> category = categoryRepository.findById(name);
        return category.isPresent();
    }

    public ResponseEntity<CategoryDto> createCategory(String name) {
        Optional<Category> category = categoryRepository.findById(name);
        log.info("Category: {}", category);
        if (category.isEmpty()) {
            Category newCategory = Category.builder().name(name).build();
            log.info("Category created: {}", newCategory);
            categoryRepository.save(newCategory);
            return ResponseEntity.ok(new CategoryDto(newCategory));
        }
        throw new RuntimeException("Category already exists");
    }
}
