package com.se.ecommerce.dto.cart;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private Long productId;
    private Long quantity;
}
