package com.formos.stripe.repository;

import com.formos.stripe.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {}
