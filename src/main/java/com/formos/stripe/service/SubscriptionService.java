package com.formos.stripe.service;

import com.formos.stripe.domain.Customer;
import com.formos.stripe.domain.Subscription;
import com.formos.stripe.service.dto.product.AdminProductRequest;
import com.formos.stripe.service.dto.subscription.AdminSubscriptionRequest;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionService {
    String createSubscription(AdminSubscriptionRequest request);

    String updateSubscription(String id, AdminSubscriptionRequest request);

    Map getSubscriptionInvoiceUrl(String id);

    String cancelSubscription(String id);

    Page<Subscription> findAll(Pageable pageable);

    Page<Subscription> findAlll(Pageable pageable);

    Subscription getSubscriptionDetail(String id);
}
