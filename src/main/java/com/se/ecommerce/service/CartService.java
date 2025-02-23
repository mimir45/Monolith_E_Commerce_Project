package com.se.ecommerce.service;

import com.se.ecommerce.dto.cart.CartDto;
import com.se.ecommerce.dto.cart.CartItemRequest;
import com.se.ecommerce.model.Cart;
import com.se.ecommerce.model.CartItem;
import com.se.ecommerce.model.Product;
import com.se.ecommerce.model.User;
import com.se.ecommerce.repository.CartItemRepository;
import com.se.ecommerce.repository.CartRepository;
import com.se.ecommerce.repository.ProductRepository;
import com.se.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserService userService, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;

        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
    }

    public void createCart() {
        Cart newcart = Cart.builder().build();
        cartRepository.save(newcart);
    }

    public ResponseEntity<CartDto> getCart(Long id) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(id);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            CartDto cartDto = new CartDto(cartOptional.get());
            return ResponseEntity.ok(cartDto);
        }
        //todo custom exception
        throw new RuntimeException("Cart not found");
    }

    @Transactional
    public ResponseEntity<CartDto> addItemToCart(Long userId, CartItemRequest request) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });

        Optional<Product> product = productRepository.findById(request.getProductId());
        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        Double totalPrice = product.get().getPrice() * request.getQuantity();

        CartItem cartItem = CartItem.builder()
                .product(product.get())
                .quantity(request.getQuantity())
                .totalPrice(totalPrice)
                .build();
        cartItemRepository.save(cartItem);
        cart.getItems().add(cartItem);
        cartRepository.save(cart);
        CartDto cartDto = new CartDto(cart);
        return ResponseEntity.ok(cartDto);
    }
 }

