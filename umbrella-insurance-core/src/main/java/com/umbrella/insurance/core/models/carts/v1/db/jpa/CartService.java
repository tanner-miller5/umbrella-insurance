package com.umbrella.insurance.core.models.carts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Cart;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Cart saveCart(Cart cartRecord);
    List<Cart> getCarts();
    Cart updateCart(Cart cartRecord);
    void deleteCart(Long cartId);
    Optional<Cart> getCartById(Long cartId);
    void deleteCartByUserId(Long cartId);
    Optional<Cart> getCartByUserId(Long userId);
}
