package com.webmarket.microservices.core.backend.repositories;

import com.webmarket.microservices.core.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle (String title);

    @Query("select c from Category c join fetch c.products where c.id = :id")
    Optional<Category> findCategoryByIdWithProducts(Long id);
}
