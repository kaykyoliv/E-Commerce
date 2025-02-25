package com.ecommerce.project.controller;

import com.ecommerce.project.dto.CategoryDTO;
import com.ecommerce.project.dto.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(Pageable pageable){
        CategoryResponse allCategories = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok().body(allCategories);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        categoryDTO = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@Valid @PathVariable Long categoryId){
        CategoryDTO categoryDTO = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId){
            CategoryDTO savedCategory = categoryService.updateCategory(categoryDTO, categoryId);
            return ResponseEntity.ok().body(savedCategory);
    }

}
