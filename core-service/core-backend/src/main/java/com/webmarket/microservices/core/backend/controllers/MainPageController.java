package com.webmarket.microservices.core.backend.controllers;

import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import com.webmarket.microservices.core.api.ProductDto;
import com.webmarket.microservices.core.backend.converters.ProductConverter;
import com.webmarket.microservices.core.backend.entities.Product;
import com.webmarket.microservices.core.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/mainPage")
@RequiredArgsConstructor
@Slf4j
public class MainPageController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id){
        Product product = productService.findById(id).orElseThrow(() ->new ResourceNotFoundException("Product id:" + id + " not found"));
        return productConverter.entityToDto(product);
    }
}
