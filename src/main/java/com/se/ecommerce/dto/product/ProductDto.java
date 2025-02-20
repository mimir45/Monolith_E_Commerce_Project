package com.se.ecommerce.dto.product;

import com.se.ecommerce.dto.category.CategoryDto;
import com.se.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer stock;
    private String category;

    public ProductDto(Product newProduct) {
        this.name = newProduct.getName();
        this.description = newProduct.getDescription();
        this.price = newProduct.getPrice();
        this.imageUrl = newProduct.getImageUrl();
        this.stock = newProduct.getStock();
        this.category = newProduct.getCategory().getName();
    }
}
