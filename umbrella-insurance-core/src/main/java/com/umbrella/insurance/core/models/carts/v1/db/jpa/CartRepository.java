package com.umbrella.insurance.core.models.carts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "SELECT * FROM carts c WHERE c.user_id = :userId ORDER BY s.created_date_time desc limit 1", nativeQuery = true)
    Optional<Cart> findCartByUserId(Long userId);
    void deleteCartByUserId(Long userId);
}
