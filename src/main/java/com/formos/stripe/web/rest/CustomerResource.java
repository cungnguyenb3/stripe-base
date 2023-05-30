package com.formos.stripe.web.rest;

import com.formos.stripe.service.CustomerService;
import com.formos.stripe.service.dto.customer.AdminCustomerRequest;
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
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private final CustomerService customerService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<String> createCustomer(@RequestBody AdminCustomerRequest request) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", request);
        String response = customerService.createCustomer(request);
        return ResponseEntity
            .created(new URI("/api/admin/users/customers"))
            .headers(HeaderUtil.createAlert(applicationName, "productManagement.created", response))
            .body(response);
    }
}
