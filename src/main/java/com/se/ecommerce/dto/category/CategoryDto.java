package com.se.ecommerce.dto.category;

import com.se.ecommerce.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String name;
    public CategoryDto(Category category) {
        this.name = category.getName();
    }
}
