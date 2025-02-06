package com.ecommerce.project.service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.dto.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);
}