package com.ecommerce.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;

    @NotBlank
    @Size(min = 3, max = 80, message = "Product name must contain atleast 3 characters")
    private String productName;
    private String image;
    @Size(min = 6, message = "Product name must contain atleast 6 characters")
    private String description;
    private Integer quantity;
    @NotNull(message = "Required field")
    @Positive(message = "The price must be positive")
    private double price;
    private double discount;
    private double specialPrice;

}
