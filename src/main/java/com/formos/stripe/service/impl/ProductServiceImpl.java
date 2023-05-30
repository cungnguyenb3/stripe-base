package com.formos.stripe.service.impl;

import com.formos.stripe.repository.ProductRepository;
import com.formos.stripe.service.ProductService;
import com.formos.stripe.service.dto.product.AdminProductRequest;
import com.stripe.Stripe;
import com.stripe.model.Product;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final String SECRET_KEY =
        "sk_test_51LNDJNBBOGnAYk5tqzvjlqWk64NjehIIUKUCoWCJrrY5wk10wa3ovvDHS29J89DnlnbrWJFGYXuzVvMTa4WlCBcr00XyKnr5BM";

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String createProduct(AdminProductRequest request) {
        com.stripe.model.Product stripeProduct = null;
        try {
            Stripe.apiKey = SECRET_KEY;

            Map<String, Object> params = new HashMap<>();
            params.put("name", request.getName());
            params.put("description", request.getDescription());

            stripeProduct = Product.create(params);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        com.formos.stripe.domain.Product product = new com.formos.stripe.domain.Product();
        product.setId(stripeProduct.getId());
        product.setName(stripeProduct.getName());
        product.setDescription(stripeProduct.getDescription());
        productRepository.save(product);
        return product.toString();
    }
}
