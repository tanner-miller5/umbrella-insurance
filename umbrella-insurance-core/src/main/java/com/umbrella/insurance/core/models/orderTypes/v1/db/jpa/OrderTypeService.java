package com.umbrella.insurance.core.models.orderTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.OrderType;

import java.util.List;
import java.util.Optional;

public interface OrderTypeService {
    OrderType saveOrderType(OrderType orderType);
    List<OrderType> getOrderTypes();
    OrderType updateOrderType(OrderType orderType);
    void deleteOrderType(Long orderTypeId);
    Optional<OrderType> getOrderTypeByOrderTypeName(String orderTypeName);
    void deleteOrderTypeByOrderTypeName(String orderTypeName);
    Optional<OrderType> getOrderTypeById(Long orderTypeId);
}
