package com.umbrella.insurance.core.models.users.paymentTypes.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.PaymentType;

import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    Optional<PaymentType> findPaymentTypeByPaymentTypeName(String paymentTypeName);
    void deletePaymentTypeByPaymentTypeName(String paymentTypeName);
}
