package com.umbrella.insurance.core.models.users.paymentTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService {
    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    @Override
    public PaymentType savePaymentType(PaymentType paymentType) {
        return paymentTypeRepository.save(paymentType);
    }

    @Override
    public List<PaymentType> getPaymentTypes() {
        return paymentTypeRepository.findAll();
    }

    @Override
    public PaymentType updatePaymentType(PaymentType paymentType) {
        return paymentTypeRepository.save(paymentType);
    }

    @Override
    public void deletePaymentType(Long paymentTypeId) {
        paymentTypeRepository.deleteById(paymentTypeId);
    }

    @Override
    public Optional<PaymentType> findPaymentTypeByPaymentTypeName(String paymentTypeName) {
        return paymentTypeRepository.findPaymentTypeByPaymentTypeName(paymentTypeName);
    }

    @Override
    public Optional<PaymentType> findPaymentTypeByPaymentTypeId(Long paymentTypeId) {
        return paymentTypeRepository.findById(paymentTypeId);
    }

    @Override
    public void deletePaymentTypeByPaymentTypeName(String paymentTypeName) {
        paymentTypeRepository.deletePaymentTypeByPaymentTypeName(paymentTypeName);
    }
}
