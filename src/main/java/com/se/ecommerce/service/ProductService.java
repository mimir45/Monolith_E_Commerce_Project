package com.se.ecommerce.service;

import com.se.ecommerce.dto.product.ProductCreateRequest;
import com.se.ecommerce.dto.product.ProductDto;
import com.se.ecommerce.model.Product;
import com.se.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository ) {
        this.productRepository = productRepository;

    }

    public ResponseEntity<List<ProductDto>> getProducts(){
        log.info("Get products executed");
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream().map(ProductDto::new).toList();
        log.info("Get products returned {}", productDtos);
        return ResponseEntity.ok(productDtos);
    }

    @Transactional
    public ResponseEntity<ProductDto> createProduct(ProductCreateRequest request) {
       Optional<Product> optionalProduct = productRepository.findByName(request.getName());
        log.info("Product found: {}", optionalProduct);
        if (optionalProduct.isEmpty()) {
            Product newProduct = new Product(request);
            productRepository.save(newProduct);
            log.info("Product created: {}", newProduct);
            return ResponseEntity.ok(new ProductDto(newProduct));
        }

        //todo exceoption
        throw new RuntimeException("Product with name " + request.getName() + " already exists");
    }
}
