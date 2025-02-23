package com.se.ecommerce.controller;

import com.se.ecommerce.dto.cart.CartDto;
import com.se.ecommerce.dto.cart.CartItemRequest;
import com.se.ecommerce.dto.cart.UpdateCartItemRequest;
import com.se.ecommerce.service.CartService;
import com.se.ecommerce.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService, CategoryService categoryService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long id) {

        return cartService.getCart(id);
    }
    @PostMapping("/items")
    public ResponseEntity<CartDto> addItemToCart( @RequestParam Long userid, @RequestBody CartItemRequest request) {
        return  cartService.addItemToCart(userid, request);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<CartDto> updateItemToCart(@PathVariable Long id,@RequestBody UpdateCartItemRequest request) {
        return  cartService.updateItemQuantity(id,request);

    }
    @DeleteMapping("items/{id}")
    public ResponseEntity<Void> deleteItemFromCart(@PathVariable Long id) {
        return  cartService.deleteCartItem(id);
    }


}
