package com.webmarket.microservices.core.backend.controllers;

import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import com.webmarket.microservices.core.api.ProductDto;
import com.webmarket.microservices.core.backend.converters.ProductConverter;
import com.webmarket.microservices.core.backend.entities.Product;
import com.webmarket.microservices.core.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;


    @GetMapping
    public Page<ProductDto> find(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(name = "partName", required = false) String partName,
            @RequestParam(name = "category", required = false) String category
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.find(page,minPrice,maxPrice,partName,category).map(productConverter::entityToDto);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id){
        Product product = productService.findById(id).orElseThrow(() ->new ResourceNotFoundException("Product id:" + id + " not found"));
        return productConverter.entityToDto(product);
        //return new ProductDto(productService.findById(id).orElseThrow(() ->new ResourceNotFoundException("Product id:" + id + " not found")));
    }


}


