package com.ecommerce.project.service;

import com.ecommerce.project.dto.ProductDTO;
import com.ecommerce.project.dto.ProductResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);
    ProductResponse getAllProducts(Pageable pageable);
    ProductResponse getAllProductsByCategory(Pageable pageable, Long categoryId);
    ProductResponse getProductByKeyword(String keyword, Pageable pageable);
}