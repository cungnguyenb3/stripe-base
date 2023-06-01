package com.formos.stripe.repository;

import com.formos.stripe.domain.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
    Page<PaymentMethod> findAllByCustomerId(Pageable pageable, String customerId);
}
