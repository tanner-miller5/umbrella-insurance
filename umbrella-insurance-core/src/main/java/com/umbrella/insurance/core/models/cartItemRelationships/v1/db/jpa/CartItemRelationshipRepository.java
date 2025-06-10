package com.umbrella.insurance.core.models.cartItemRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.CartItemRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRelationshipRepository  extends JpaRepository<CartItemRelationship, Long> {
    Optional<CartItemRelationship> findCartItemRelationshipByCartIdAndItemId(Long cartId, Long itemId);
    void deleteCartItemRelationshipByCartIdAndItemId(Long cartId, Long itemId);

}
