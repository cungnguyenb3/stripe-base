package com.formos.stripe.service;

import com.formos.stripe.service.dto.payment_method.AdminPaymentMethodRequest;

public interface PaymentMethodService {
    String createPaymentMethod(AdminPaymentMethodRequest request);
}
