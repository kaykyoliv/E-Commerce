package com.ecommerce.project.service;

import com.ecommerce.project.dto.CategoryDTO;
import com.ecommerce.project.dto.CategoryResponse;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryResponse getAllCategories(Pageable pageable);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    void deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
