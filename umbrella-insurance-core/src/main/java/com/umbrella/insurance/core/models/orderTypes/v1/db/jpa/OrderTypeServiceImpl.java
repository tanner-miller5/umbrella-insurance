package com.umbrella.insurance.core.models.orderTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderTypeServiceImpl implements OrderTypeService {

    @Autowired
    OrderTypeRepository orderTypeRepository;

    @Override
    public OrderType saveOrderType(OrderType orderType) {
        return orderTypeRepository.save(orderType);
    }

    @Override
    public List<OrderType> getOrderTypes() {
        return orderTypeRepository.findAll();
    }

    @Override
    public OrderType updateOrderType(OrderType orderType) {
        return orderTypeRepository.save(orderType);
    }

    @Override
    public void deleteOrderType(Long orderTypeId) {
        orderTypeRepository.deleteById(orderTypeId);
    }

    @Override
    public Optional<OrderType> getOrderTypeByOrderTypeName(String orderTypeName) {
        return orderTypeRepository.findOrderTypeByOrderTypeName(orderTypeName);
    }

    @Override
    public void deleteOrderTypeByOrderTypeName(String orderTypeName) {
        orderTypeRepository.deleteOrderTypeByOrderTypeName(orderTypeName);
    }

    @Override
    public Optional<OrderType> getOrderTypeById(Long orderTypeId) {
        return orderTypeRepository.findById(orderTypeId);
    }
}
