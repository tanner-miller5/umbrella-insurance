package com.umbrella.insurance.core.models.orderTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Long> {
    Optional<OrderType> findOrderTypeByOrderTypeName(String orderTypeName);
    void deleteOrderTypeByOrderTypeName(String orderTypeName);
}
