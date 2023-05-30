package com.formos.stripe.service.impl;

import com.formos.stripe.domain.Customer;
import com.formos.stripe.domain.Price;
import com.formos.stripe.domain.Product;
import com.formos.stripe.domain.Subscription;
import com.formos.stripe.repository.CustomerRepository;
import com.formos.stripe.repository.PriceRepository;
import com.formos.stripe.repository.ProductRepository;
import com.formos.stripe.repository.SubscriptionRepository;
import com.formos.stripe.service.SubscriptionService;
import com.formos.stripe.service.dto.subscription.AdminSubscriptionRequest;
import com.stripe.Stripe;
import com.stripe.model.Invoice;
import com.stripe.param.SubscriptionCancelParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final String SECRET_KEY =
        "sk_test_51LNDJNBBOGnAYk5tqzvjlqWk64NjehIIUKUCoWCJrrY5wk10wa3ovvDHS29J89DnlnbrWJFGYXuzVvMTa4WlCBcr00XyKnr5BM";

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final PriceRepository priceRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(
        ProductRepository productRepository,
        CustomerRepository customerRepository,
        PriceRepository priceRepository,
        SubscriptionRepository subscriptionRepository
    ) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.priceRepository = priceRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public String createSubscription(AdminSubscriptionRequest request) {
        Stripe.apiKey = SECRET_KEY;
        Product product = productRepository.findById(request.getProductId()).orElse(null);
        Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        Price price = priceRepository.findFirstByProductId(product.getId()).orElse(null);

        com.stripe.model.Subscription stripeSubscription = null;
        try {
            List<Object> items = new ArrayList<>();
            Map<String, Object> item1 = new HashMap<>();
            item1.put("price", price.getId());

            items.add(item1);
            Map<String, Object> params = new HashMap<>();
            params.put("customer", customer.getId());
            params.put("items", items);
            params.put("default_payment_method", customer.getPaymentMethod().getId());

            stripeSubscription = com.stripe.model.Subscription.create(params);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        Subscription subscription = new Subscription();
        subscription.setId(stripeSubscription.getId());
        subscription.setDescription(subscription.getDescription());
        subscription.setActive(true);
        subscription.setProduct(product);
        subscription.setCustomer(customer);
        subscriptionRepository.save(subscription);

        return subscription.toString();
    }

    @Override
    public String getSubscriptionInvoiceUrl(String id) {
        Stripe.apiKey = SECRET_KEY;

        String invoiceUrl = "";
        try {
            com.stripe.model.Subscription subscription = com.stripe.model.Subscription.retrieve(id);
            Invoice invoice = Invoice.retrieve(subscription.getLatestInvoice());

            invoiceUrl = invoice.getInvoicePdf();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        return invoiceUrl;
    }

    @Override
    public String cancelSubscription(String id) {
        Stripe.apiKey = SECRET_KEY;

        try {
            com.stripe.model.Subscription resource = com.stripe.model.Subscription.retrieve(id);
            SubscriptionCancelParams params = SubscriptionCancelParams.builder().build();

            com.stripe.model.Subscription subscription = resource.cancel(params);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        return "Cancel subscription successfully";
    }
}
