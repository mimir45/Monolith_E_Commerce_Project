package com.se.ecommerce.controller;

import com.se.ecommerce.dto.product.ProductCreateRequest;
import com.se.ecommerce.dto.product.ProductDto;
import com.se.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductCreateRequest request) {
        return productService.createProduct(request);

    }


}
