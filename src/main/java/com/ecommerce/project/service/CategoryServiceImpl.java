package com.ecommerce.project.service;

import com.ecommerce.project.dto.CategoryDTO;
import com.ecommerce.project.dto.CategoryResponse;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        if(categoryPage.isEmpty()) {
            throw new APIException("No categories found");
        }

        Page<CategoryDTO> categoryDTO = categoryPage.map(category -> modelMapper.map(category , CategoryDTO.class));
        return new CategoryResponse(categoryDTO);
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        validateCategoryExists(categoryDTO.getCategoryName());
        Category category = modelMapper.map(categoryDTO, Category.class);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category categoryFromDb = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        if (!categoryFromDb.getCategoryName().equals(categoryDTO.getCategoryName())) {
            validateCategoryExists(categoryDTO.getCategoryName());
        }

        categoryFromDb.setCategoryName(categoryDTO.getCategoryName());
        Category savedCategory = categoryRepository.save(categoryFromDb);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    private void validateCategoryExists(String categoryName) {
        if (categoryRepository.findByCategoryName(categoryName) != null) {
            throw new APIException("Category with the name " + categoryName + " already exists");
        }
    }
}
