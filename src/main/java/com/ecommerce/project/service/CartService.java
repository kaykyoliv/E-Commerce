package com.ecommerce.project.service;

import com.ecommerce.project.dto.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
