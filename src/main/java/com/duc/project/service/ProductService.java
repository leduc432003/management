package com.duc.project.service;

import com.duc.project.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Boolean create(Product product);
    Product findById(Integer id);
    Boolean update(Product product);
    Boolean delete(Integer id);
    List<Product> searchProduct(String keyword);
    Page<Product> searchProduct(String keyword, Integer pageNo);
    Page<Product> getAll(Integer pageNo);
}
