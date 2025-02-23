package com.ecommerce.project.service;

import com.ecommerce.project.dto.OrderDTO;
import com.ecommerce.project.dto.OrderRequestDTO;

public interface OrderService {
    OrderDTO placeOrder(String emailId, String paymentMethod, OrderRequestDTO orderRequestDTO);
}
