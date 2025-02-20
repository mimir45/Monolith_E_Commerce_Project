package com.se.ecommerce.model;

import com.se.ecommerce.dto.category.CategoryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    private String name;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products;


}
