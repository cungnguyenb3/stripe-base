package com.formos.stripe.service;

import com.formos.stripe.domain.PaymentMethod;
import com.formos.stripe.service.dto.payment_method.AdminPaymentMethodRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentMethodService {
    String createPaymentMethod(AdminPaymentMethodRequest request);
    Page<PaymentMethod> findAll(Pageable pageable, String customerId);
}
