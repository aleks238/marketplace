package com.webmarket.microservices.core.backend.services;

import com.webmarket.microservices.core.backend.entities.Product;
import com.webmarket.microservices.core.backend.repositories.ProductRepository;
import com.webmarket.microservices.core.backend.repositories.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> find(Integer page, Integer minPrice, Integer maxPrice, String partName, String category) {
        Specification<Product> specification = Specification.where(null);
        if (minPrice != null) {
            specification = specification.and(ProductsSpecifications.priceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ProductsSpecifications.priceLessThanOrEqualTo(maxPrice));
        }
        if (partName != null) {
            specification = specification.and(ProductsSpecifications.nameLike(partName));
        }
        if (category != null) {
            specification = specification.and(ProductsSpecifications.categoryTitleLike(category));
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 5));
    }


    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


}
