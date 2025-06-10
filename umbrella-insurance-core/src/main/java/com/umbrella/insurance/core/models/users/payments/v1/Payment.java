package com.umbrella.insurance.core.models.users.payments.v1;

import com.umbrella.insurance.core.models.entities.PaymentType;
import lombok.Data;

@Data
public class Payment {
    Long paymentId;
    PaymentType paymentType;
    Long paymentTypeId;
    Double paymentAmount;
    Long cartId;

}
