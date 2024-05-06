package com.webmarket.microservices.core.backend.converters;

import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import com.webmarket.microservices.core.api.ProductDto;
import com.webmarket.microservices.core.backend.entities.Product;
import com.webmarket.microservices.core.backend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final CategoryService categoryService;

    public ProductDto entityToDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getCategory().getTitle()
        );
    }

    public Product dtoToEntity(ProductDto productDto){
        return new Product(productDto.getId(),productDto.getTitle(), productDto.getPrice(), categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(()->new ResourceNotFoundException("Категория не найдена: " + productDto.getCategoryTitle())));
    }
}
