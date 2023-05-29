package com.formos.stripe.service;

import com.formos.stripe.service.dto.product.AdminProductRequest;

public interface ProductService {
    String createProduct(AdminProductRequest request);
}
