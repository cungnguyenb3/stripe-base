package com.formos.stripe.service.impl;

import com.formos.stripe.domain.PaymentMethod;
import com.formos.stripe.repository.CustomerRepository;
import com.formos.stripe.repository.PaymentMethodRepository;
import com.formos.stripe.service.CustomerService;
import com.formos.stripe.service.dto.customer.AdminCustomerRequest;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final String SECRET_KEY =
        "sk_test_51LNDJNBBOGnAYk5tqzvjlqWk64NjehIIUKUCoWCJrrY5wk10wa3ovvDHS29J89DnlnbrWJFGYXuzVvMTa4WlCBcr00XyKnr5BM";

    private final CustomerRepository customerRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, PaymentMethodRepository paymentMethodRepository) {
        this.customerRepository = customerRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public String createCustomer(AdminCustomerRequest request) {
        com.stripe.model.Customer stripeCustomer = null;
        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId()).orElse(null);

        try {
            Stripe.apiKey = SECRET_KEY;

            Map<String, Object> params = new HashMap<>();
            params.put("name", request.getName());
            params.put("description", request.getDescription());
            params.put("email", request.getEmail());
            params.put("phone", request.getPhone());
            params.put("payment_method", paymentMethod.getId());

            stripeCustomer = Customer.create(params);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        com.formos.stripe.domain.Customer customer = new com.formos.stripe.domain.Customer();
        customer.setId(stripeCustomer.getId());
        customer.setName(stripeCustomer.getName());
        customer.setDescription(stripeCustomer.getDescription());
        customer.setEmail(stripeCustomer.getEmail());
        customer.setPhone(stripeCustomer.getPhone());
        customer.setPaymentMethod(paymentMethod);

        customerRepository.save(customer);
        return customer.toString();
    }
}
