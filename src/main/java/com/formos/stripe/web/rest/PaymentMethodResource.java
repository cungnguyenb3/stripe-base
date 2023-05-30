package com.formos.stripe.web.rest;

import com.formos.stripe.service.PaymentMethodService;
import com.formos.stripe.service.dto.payment_method.AdminPaymentMethodRequest;
import com.formos.stripe.service.dto.product.AdminProductRequest;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/admin")
public class PaymentMethodResource {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodResource(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping("/payment-methods")
    public ResponseEntity<String> createPaymentMethod(@RequestBody AdminPaymentMethodRequest request) throws URISyntaxException {
        log.debug("REST request to save Payment method : {}", request);
        String response = paymentMethodService.createPaymentMethod(request);
        return ResponseEntity
            .created(new URI("/api/admin/users/payment_methods"))
            .headers(HeaderUtil.createAlert(applicationName, "productManagement.created", response))
            .body(response);
    }
}
