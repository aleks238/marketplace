package com.webmarket.microservices.core.api;

import java.util.List;
public class CategoryDto {
    private Long id;
    private String title;
    private List<ProductDto> products;

    public CategoryDto(Long id, String title, List<ProductDto> products) {
        this.id = id;
        this.title = title;
        this.products = products;
    }

    public CategoryDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
