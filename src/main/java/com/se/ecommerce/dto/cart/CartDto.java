package com.se.ecommerce.dto.cart;

import com.se.ecommerce.model.Cart;
import com.se.ecommerce.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private List<CartItemDto> cartItems;

    public CartDto(Cart cart) {
        this.cartItems = cart.getItems().stream().map(CartItemDto::new).toList();
    }
}
