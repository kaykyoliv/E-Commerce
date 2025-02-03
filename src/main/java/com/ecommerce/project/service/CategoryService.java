package com.ecommerce.project.service;

import com.ecommerce.project.dto.CategoryResponse;
import com.ecommerce.project.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}
