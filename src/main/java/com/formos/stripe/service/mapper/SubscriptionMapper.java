package com.formos.stripe.service.mapper;

import com.formos.stripe.domain.Subscription;
import com.formos.stripe.service.dto.subscription.AdminSubscriptionResponse;

public interface SubscriptionMapper {
    AdminSubscriptionResponse toResponse(Subscription subscription);
}
