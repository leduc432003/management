package com.duc.project.repository;

import com.duc.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c from Category c WHERE c.categoryName like %?1%")
    List<Category> searchCategory(String keyword);
}
