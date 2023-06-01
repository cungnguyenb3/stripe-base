package com.formos.stripe.web.rest;

import com.formos.stripe.domain.Subscription;
import com.formos.stripe.service.SubscriptionService;
import com.formos.stripe.service.dto.subscription.AdminSubscriptionRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

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

    @GetMapping(value = "/subscriptions")
    public ResponseEntity<List<Subscription>> findAll(Pageable pageable) {
        Page<Subscription> res = subscriptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), res);
        return new ResponseEntity<>(res.getContent(), headers, HttpStatus.OK);
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

    @PutMapping("/subscriptions/{id}")
    public ResponseEntity<String> updateSubscription(@PathVariable String id, @RequestBody AdminSubscriptionRequest request)
        throws URISyntaxException {
        log.debug("REST request to save Subscription : {}", request);
        String response = subscriptionService.updateSubscription(id, request);
        return ResponseEntity
            .created(new URI("/api/admin/subscriptions"))
            .headers(HeaderUtil.createAlert(applicationName, "productManagement.created", response))
            .body(response);
    }

    @GetMapping("/subscriptions/{id}/invoice/download")
    public ResponseEntity<Map> downloadInvoice(@PathVariable String id) {
        Map response = subscriptionService.getSubscriptionInvoiceUrl(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/subscriptions/{id}/cancel")
    public ResponseEntity<String> cancelSubscription(@PathVariable String id) {
        String response = subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/subscriptions/{id}")
    public ResponseEntity<Subscription> findById(@PathVariable String id) {
        Subscription response = subscriptionService.getSubscriptionDetail(id);
        return ResponseEntity.ok(response);
    }
}
