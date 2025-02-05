package com.ecommerce.project.service;

import com.ecommerce.project.dto.CategoryDTO;
import com.ecommerce.project.dto.CategoryResponse;
import com.ecommerce.project.model.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Pageable pageable);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
