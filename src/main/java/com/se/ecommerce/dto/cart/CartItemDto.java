package com.se.ecommerce.dto.cart;

import com.se.ecommerce.dto.product.ProductDto;
import com.se.ecommerce.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private ProductDto product;
    private int quantity;
    private Double totalPrice;
    public CartItemDto(CartItem cartItem) {
        this.product = new ProductDto(cartItem.getProduct());
        this.quantity = cartItem.getQuantity();
        this.totalPrice = cartItem.getTotalPrice();
    }


}
