package com.duc.project.repository;

import com.duc.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT c from Product c WHERE c.productName like %?1%")
    List<Product> searchCategory(String keyword);
}
