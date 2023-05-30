package com.formos.stripe.service.impl;

import com.formos.stripe.repository.PaymentMethodRepository;
import com.formos.stripe.service.PaymentMethodService;
import com.formos.stripe.service.dto.payment_method.AdminPaymentMethodRequest;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final String SECRET_KEY =
        "sk_test_51LNDJNBBOGnAYk5tqzvjlqWk64NjehIIUKUCoWCJrrY5wk10wa3ovvDHS29J89DnlnbrWJFGYXuzVvMTa4WlCBcr00XyKnr5BM";

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public String createPaymentMethod(AdminPaymentMethodRequest request) {
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
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        com.formos.stripe.domain.PaymentMethod paymentMethod = new com.formos.stripe.domain.PaymentMethod();
        paymentMethod.setId(stripePaymentMethod.getId());
        paymentMethod.setCard(stripePaymentMethod.getCard().getDescription() + " " + stripePaymentMethod.getCard().getBrand());
        paymentMethod.setType(stripePaymentMethod.getType());

        paymentMethodRepository.save(paymentMethod);
        return paymentMethod.toString();
    }
}
