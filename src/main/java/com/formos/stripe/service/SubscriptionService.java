package com.formos.stripe.service;

import com.formos.stripe.service.dto.product.AdminProductRequest;
import com.formos.stripe.service.dto.subscription.AdminSubscriptionRequest;

public interface SubscriptionService {
    String createSubscription(AdminSubscriptionRequest request);

    String getSubscriptionInvoiceUrl(String id);

    String cancelSubscription(String id);
}
