package com.formos.stripe.service.impl;

import com.formos.stripe.domain.Product;
import com.formos.stripe.repository.PriceRepository;
import com.formos.stripe.repository.ProductRepository;
import com.formos.stripe.service.PriceService;
import com.formos.stripe.service.dto.price.AdminPriceRequest;
import com.stripe.Stripe;
import com.stripe.model.Price;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {

    private final String SECRET_KEY =
        "sk_test_51LNDJNBBOGnAYk5tqzvjlqWk64NjehIIUKUCoWCJrrY5wk10wa3ovvDHS29J89DnlnbrWJFGYXuzVvMTa4WlCBcr00XyKnr5BM";

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    public PriceServiceImpl(ProductRepository productRepository, PriceRepository priceRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    public String createPrice(AdminPriceRequest request) {
        Stripe.apiKey = SECRET_KEY;
        Product product = productRepository.findById(request.getProductId()).orElse(null);

        Price stripePrice = null;
        try {
            Map<String, Object> recurring = new HashMap<>();
            recurring.put("interval", "month");

            Map<String, Object> params = new HashMap<>();
            params.put("unit_amount", request.getUnitAmount());
            params.put("currency", request.getCurrency());
            params.put("recurring", recurring);
            params.put("product", product.getId());

            stripePrice = Price.create(params);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        com.formos.stripe.domain.Price price = new com.formos.stripe.domain.Price();
        price.setId(stripePrice.getId());
        price.setCurrency(stripePrice.getCurrency());
        price.setUnitAmount(stripePrice.getUnitAmount());
        price.setProduct(product);

        priceRepository.save(price);

        return price.toString();
    }
}
