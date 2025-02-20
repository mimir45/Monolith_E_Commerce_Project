package com.se.ecommerce.dto.product;

import com.se.ecommerce.dto.category.CategoryDto;
import lombok.Data;



@Data
public class ProductCreateRequest {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
    private Integer stock;
}
