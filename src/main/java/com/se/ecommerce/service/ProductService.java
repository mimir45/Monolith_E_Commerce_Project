package com.se.ecommerce.service;

import com.se.ecommerce.dto.product.ProductCreateRequest;
import com.se.ecommerce.dto.product.ProductDto;
import com.se.ecommerce.model.Category;
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
    private final CategoryService categoryService;
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;

        this.categoryService = categoryService;
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
            boolean  optionalCategory = categoryService.categoryExists(request.getCategory());
           if (!optionalCategory) {
               categoryService.createCategory(request.getCategory());
           }
            productRepository.save(newProduct);
            log.info("Product created: {}", newProduct);
            return ResponseEntity.ok(new ProductDto(newProduct));
        }

        //todo exceoption
        throw new RuntimeException("Product with name " + request.getName() + " already exists");
    }

    public ResponseEntity<ProductDto> getById(Long id) {
        log.info("Get product by id: {}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(new ProductDto(optionalProduct.get()));
        }
        //todo custom exception
        throw new RuntimeException("Product with id " + id + " not found");
    }

    public ResponseEntity<ProductDto> updateProduct(Long id, ProductCreateRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = new Product(request);
            product.setId(id);
            productRepository.save(product);
            return ResponseEntity.ok(new ProductDto(product));
        }
        //todo custom exception
        throw new RuntimeException("Product with id " + id + " not found");
    }

    public ResponseEntity<ProductDto> addStock(Long id, Integer stock) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStock(product.getStock()+stock);
            productRepository.save(product);
            return ResponseEntity.ok(new ProductDto(product));
        }
        throw new RuntimeException("Product with id " + id + " not found");
    }

    public ResponseEntity<Void> deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
            return ResponseEntity.noContent().build();
        }

        throw new RuntimeException("Product with id " + id + " not found");
    }
}
