package com.umbrella.insurance.core.models.users.paymentTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PaymentType;

import java.util.List;
import java.util.Optional;

public interface PaymentTypeService {
    PaymentType savePaymentType(PaymentType paymentType);
    List<PaymentType> getPaymentTypes();
    PaymentType updatePaymentType(PaymentType paymentType);
    void deletePaymentType(Long paymentTypeId);
    Optional<PaymentType> findPaymentTypeByPaymentTypeName(String paymentTypeName);
    Optional<PaymentType> findPaymentTypeByPaymentTypeId(Long paymentTypeId);
    void deletePaymentTypeByPaymentTypeName(String paymentTypeName);
}
