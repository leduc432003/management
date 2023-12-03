package com.duc.project.service;

import com.duc.project.model.Category;
import com.duc.project.model.Product;
import com.duc.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Boolean create(Product product) {
        try {
            this.productRepository.save(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Product findById(Integer id) {
        return this.productRepository.findById(id).get();
    }

    @Override
    public Boolean update(Product product) {
        try {
            this.productRepository.save(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            this.productRepository.delete(findById(id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        return this.productRepository.searchCategory(keyword);
    }

    @Override
    public Page<Product> searchProduct(String keyword, Integer pageNo) {
        List list = this.searchProduct(keyword);

        Pageable pageable = PageRequest.of(pageNo - 1, 2);

        Integer start = (int) pageable.getOffset();
        Integer end = Math.toIntExact((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);
        return new PageImpl<Product>(list, pageable, this.searchProduct(keyword).size());
    }

    @Override
    public Page<Product> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        return this.productRepository.findAll(pageable);
    }
}
