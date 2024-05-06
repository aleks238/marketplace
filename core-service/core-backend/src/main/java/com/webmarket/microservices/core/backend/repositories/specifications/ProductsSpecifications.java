package com.webmarket.microservices.core.backend.repositories.specifications;
import com.webmarket.microservices.core.backend.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductsSpecifications {
    public static Specification<Product> priceGreaterThanOrEqualTo(Integer price){
        return(Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"),price);
    }

    public static Specification<Product> priceLessThanOrEqualTo(Integer price){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> nameLike(String partName){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->criteriaBuilder.like(root.get("title"), String.format("%%%s%%", partName));
    }

    public static Specification<Product> categoryTitleLike(String categoryTitle){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->criteriaBuilder.equal(root.get("category").get("title"), categoryTitle);

    }
}
