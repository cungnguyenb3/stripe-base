package com.formos.stripe.web.rest;

import com.formos.stripe.service.SubscriptionService;
import com.formos.stripe.service.dto.product.AdminProductRequest;
import com.formos.stripe.service.dto.subscription.AdminSubscriptionRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/admin")
public class SubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscriptionService subscriptionService;

    public SubscriptionResource(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscriptions")
    public ResponseEntity<String> createSubscription(@RequestBody AdminSubscriptionRequest request) throws URISyntaxException {
        log.debug("REST request to save Subscription : {}", request);
        String response = subscriptionService.createSubscription(request);
        return ResponseEntity
            .created(new URI("/api/admin/users/subscriptions"))
            .headers(HeaderUtil.createAlert(applicationName, "productManagement.created", response))
            .body(response);
    }

    @GetMapping("/subscriptions/{id}/invoice/download")
    public ResponseEntity<String> downloadInvoice(@PathVariable String id) {
        String response = subscriptionService.getSubscriptionInvoiceUrl(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/subscriptions/{id}/cancel")
    public ResponseEntity<String> cancelSubscription(@PathVariable String id) {
        String response = subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok(response);
    }
}
