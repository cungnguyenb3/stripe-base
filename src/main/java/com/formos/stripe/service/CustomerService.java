package com.formos.stripe.service;

import com.formos.stripe.service.dto.customer.AdminCustomerRequest;

public interface CustomerService {
    String createCustomer(AdminCustomerRequest request);
}
