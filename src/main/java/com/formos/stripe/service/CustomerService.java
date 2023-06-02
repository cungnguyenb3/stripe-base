package com.formos.stripe.service;

import com.formos.stripe.domain.Customer;
import com.formos.stripe.service.dto.customer.AdminCustomerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    String createCustomer(AdminCustomerRequest request);
    Page<Customer> findAll(Pageable pageable);
    Customer findById(String id);
}
