package com.formos.stripe.service;

import com.formos.stripe.service.dto.price.AdminPriceRequest;

public interface PriceService {
    String createPrice(AdminPriceRequest request);
}
