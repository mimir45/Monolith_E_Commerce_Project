package com.se.ecommerce.repository;

import com.se.ecommerce.model.Cart;
import com.se.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    Long  findCartIdByCartItemsId(Long cartItemId);

}
