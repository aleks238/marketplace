package com.webmarket.microservices.core.backend.services;

import com.webmarket.microservices.core.backend.entities.Category;
import com.webmarket.microservices.core.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title){
        return categoryRepository.findByTitle(title);
    }

    public Optional<Category>findById(Long id){
        return categoryRepository.findById(id);
    }

    public Optional<Category> findCategoryByIdWithProducts(Long id){
        return categoryRepository.findCategoryByIdWithProducts(id);
    }
}
