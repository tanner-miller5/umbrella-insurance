package com.umbrella.insurance.core.models.cartItemRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.CartItemRelationship;

import java.util.List;
import java.util.Optional;

public interface CartItemRelationshipService {
    CartItemRelationship saveCartItemRelationship(CartItemRelationship cartItemRelationship);
    List<CartItemRelationship> getCartItemRelationships();
    CartItemRelationship updateCartItemRelationship(CartItemRelationship cartItemRelationship);
    void deleteCartItemRelationship(Long cartItemRelationshipId);
    Optional<CartItemRelationship> getCartItemRelationshipById(Long cartItemRelationshipId);
    Optional<CartItemRelationship> getCartItemRelationshipByCartIdAndItemId(Long cartId, Long itemId);
    void deleteCartItemRelationshipByCartIdAndItemId(Long cartId, Long itemId);
}
