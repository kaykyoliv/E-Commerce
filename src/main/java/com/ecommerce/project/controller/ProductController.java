package com.ecommerce.project.controller;

import com.ecommerce.project.dto.ProductDTO;
import com.ecommerce.project.dto.ProductResponse;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId){
        ProductDTO dto = productService.addProduct(categoryId, productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(Pageable pageable){
        ProductResponse allProducts = productService.getAllProducts(pageable);
        return ResponseEntity.ok().body(allProducts);
    }

    @GetMapping("/public/products/{categoryId}")
    public ResponseEntity<ProductResponse> getAllProductsByCategory(Pageable pageable, @PathVariable Long categoryId){
        ProductResponse allProducts = productService.getAllProductsByCategory(pageable, categoryId);
        return ResponseEntity.ok().body(allProducts);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword, Pageable pageable){
        ProductResponse products = productService.getProductByKeyword(keyword, pageable);
        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId){
        ProductDTO dto = productService.updateProduct(productDTO, productId);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO dto = productService.deleteProduct(productId);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updateProduct = productService.updateProductImage(productId, image);
        return ResponseEntity.ok().body(updateProduct);
    }
}