package com.umbrella.insurance.core.models.cartItemRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.CartItemRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemRelationshipServiceImpl implements CartItemRelationshipService{
    @Autowired
    CartItemRelationshipRepository cartItemRelationshipRepository;

    @Override
    public CartItemRelationship saveCartItemRelationship(CartItemRelationship cartItemRelationship) {
        return cartItemRelationshipRepository.save(cartItemRelationship);
    }

    @Override
    public List<CartItemRelationship> getCartItemRelationships() {
        return cartItemRelationshipRepository.findAll();
    }

    @Override
    public CartItemRelationship updateCartItemRelationship(CartItemRelationship cartItemRelationship) {
        return cartItemRelationshipRepository.save(cartItemRelationship);
    }

    @Override
    public void deleteCartItemRelationship(Long cartItemRelationshipId) {
        cartItemRelationshipRepository.deleteById(
                cartItemRelationshipId);
    }

    @Override
    public Optional<CartItemRelationship> getCartItemRelationshipById(Long cartItemRelationshipId) {
        return cartItemRelationshipRepository.findById(cartItemRelationshipId);
    }

    @Override
    public Optional<CartItemRelationship> getCartItemRelationshipByCartIdAndItemId(Long cartId, Long itemId) {
        return cartItemRelationshipRepository.findCartItemRelationshipByCartIdAndItemId(cartId, itemId);
    }

    @Override
    public void deleteCartItemRelationshipByCartIdAndItemId(Long cartId, Long itemId) {
        cartItemRelationshipRepository.deleteCartItemRelationshipByCartIdAndItemId(cartId, itemId);
    }

}
