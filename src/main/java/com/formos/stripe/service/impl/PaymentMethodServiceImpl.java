package com.formos.stripe.service.impl;

import com.formos.stripe.domain.Customer;
import com.formos.stripe.repository.CustomerRepository;
import com.formos.stripe.repository.PaymentMethodRepository;
import com.formos.stripe.service.PaymentMethodService;
import com.formos.stripe.service.dto.payment_method.AdminPaymentMethodRequest;
import com.stripe.Stripe;
import com.stripe.model.PaymentMethod;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final String SECRET_KEY =
        "sk_test_51LNDJNBBOGnAYk5tqzvjlqWk64NjehIIUKUCoWCJrrY5wk10wa3ovvDHS29J89DnlnbrWJFGYXuzVvMTa4WlCBcr00XyKnr5BM";

    private final PaymentMethodRepository paymentMethodRepository;
    private final CustomerRepository customerRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository, CustomerRepository customerRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public String createPaymentMethod(AdminPaymentMethodRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        com.stripe.model.PaymentMethod stripePaymentMethod = null;
        try {
            Stripe.apiKey = SECRET_KEY;

            Map<String, Object> card = new HashMap<>();
            card.put("number", request.getNumber());
            card.put("exp_month", request.getExpMonth());
            card.put("exp_year", request.getExpYear());
            card.put("cvc", request.getCvc());

            Map<String, Object> params = new HashMap<>();
            params.put("type", "card");
            params.put("card", card);

            stripePaymentMethod = PaymentMethod.create(params);

            PaymentMethod paymentMethod = PaymentMethod.retrieve(stripePaymentMethod.getId());

            Map<String, Object> paramsAttachment = new HashMap<>();
            paramsAttachment.put("customer", customer.getId());

            PaymentMethod updatedPaymentMethod = paymentMethod.attach(paramsAttachment);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        com.formos.stripe.domain.PaymentMethod paymentMethod = new com.formos.stripe.domain.PaymentMethod();
        paymentMethod.setId(stripePaymentMethod.getId());
        paymentMethod.setCard(stripePaymentMethod.getCard().getBrand());
        paymentMethod.setType(stripePaymentMethod.getType());
        paymentMethod.setCustomer(customer);

        paymentMethodRepository.save(paymentMethod);
        return paymentMethod.toString();
    }

    @Override
    public Page<com.formos.stripe.domain.PaymentMethod> findAll(Pageable pageable, String customerId) {
        if (customerId != null) {
            return paymentMethodRepository.findAllByCustomerId(pageable, customerId);
        }

        return paymentMethodRepository.findAll(pageable);
    }
}
