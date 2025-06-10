package com.umbrella.insurance.core.models.carts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart updateCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public void deleteCartByUserId(Long userId) {
        cartRepository.deleteCartByUserId(userId);
    }

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findCartByUserId(userId);
    }
}
