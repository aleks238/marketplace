package com.webmarket.microservices.core.backend.controllers;

import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import com.webmarket.microservices.core.api.CategoryDto;
import com.webmarket.microservices.core.backend.converters.CategoryConverter;
import com.webmarket.microservices.core.backend.entities.Category;
import com.webmarket.microservices.core.backend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @GetMapping("/{id}")
    public CategoryDto findCategoryByIdWithProducts(@PathVariable Long id){
        Category category = categoryService.findCategoryByIdWithProducts(id).orElseThrow(() ->new ResourceNotFoundException("Category id " + id + " not found"));
        return categoryConverter.entityToDto(category);
    }
}
