package com.webmarket.microservices.core.backend.services;

import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import com.webmarket.microservices.core.api.ProductDto;
import com.webmarket.microservices.core.backend.entities.Category;
import com.webmarket.microservices.core.backend.entities.Product;
import com.webmarket.microservices.core.backend.repositories.ProductRepository;
import com.webmarket.microservices.core.backend.repositories.specifications.ProductsSpecificationForAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Page<Product> find(Integer page, Long id, Integer minPrice, Integer maxPrice, String partName) {
        Specification<Product> specification = Specification.where(null);
        if (id != null) {
            specification = specification.and(ProductsSpecificationForAdmin.idEqualTo(id));
        }
        if (minPrice != null) {
            specification = specification.and(ProductsSpecificationForAdmin.priceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ProductsSpecificationForAdmin.priceLessThanOrEqualTo(maxPrice));
        }
        if (partName != null) {
            specification = specification.and(ProductsSpecificationForAdmin.nameLike(partName));
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 5));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional

    public void updateProduct(ProductDto productDto) {
        Product product = findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Продукт с id:" + productDto.getId() + " не найден"));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        if (!product.getCategory().getTitle().equals(productDto.getCategoryTitle())) {
            Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория:" + productDto.getCategoryTitle() + " не найдена"));
            product.setCategory(category);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product id:" + id + " not found");
        }
    }
}
