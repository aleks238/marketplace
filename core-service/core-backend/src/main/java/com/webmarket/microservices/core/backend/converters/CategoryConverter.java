package com.webmarket.microservices.core.backend.converters;

import com.webmarket.microservices.core.api.CategoryDto;
import com.webmarket.microservices.core.backend.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryConverter {
    private final ProductConverter productConverter;

    public CategoryDto entityToDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getTitle(),
                category.getProducts().stream().map(productConverter::entityToDto).collect(Collectors.toList())
        );
    }

    public Category dtoToEntity(CategoryDto categoryDto){
        return new Category(
                categoryDto.getId(), categoryDto.getTitle(),
                categoryDto.getProducts().stream().map(productConverter::dtoToEntity).collect(Collectors.toList())
        );
    }
}
