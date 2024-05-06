package com.webmarket.microservices.core.backend.controllers;

import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import com.webmarket.microservices.core.api.ProductDto;
import com.webmarket.microservices.core.backend.converters.ProductConverter;
import com.webmarket.microservices.core.backend.entities.Category;
import com.webmarket.microservices.core.backend.entities.Product;
import com.webmarket.microservices.core.backend.exceptionHandling.Exceptions.ValidationException;
import com.webmarket.microservices.core.backend.services.AdminProductService;
import com.webmarket.microservices.core.backend.services.CategoryService;
import com.webmarket.microservices.core.backend.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminProductService adminProductService;
    private final CategoryService categoryService;
    private final ProductValidator productValidator;
    private final ProductConverter productConverter;

    @GetMapping
    public Page<ProductDto> find(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "productId", required = false) Long id,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(name = "partName", required = false) String partName
    ) {
        if (page < 1) {
            page = 1;
        }
        return adminProductService.find(page, id,minPrice,maxPrice,partName).map(productConverter::entityToDto);
    }

    @PutMapping()
    public void updateProduct(@RequestBody ProductDto productDto){
        adminProductService.updateProduct(productDto);
    }

    @PostMapping()
    public ProductDto save(@RequestBody @Validated ProductDto productDto, BindingResult bindingResult){/*@Validated provides "validation groups", i.e. group of fields in the validated bean. */
        if (bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
        }
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(()->new ResourceNotFoundException("Категория: " + productDto.getCategoryTitle() + " не найдена"));
        product.setCategory(category);
        adminProductService.save(product);
        return productConverter.entityToDto(product);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        adminProductService.deleteById(id);
    }
}
