package com.formos.stripe.web.rest;

import com.formos.stripe.service.PriceService;
import com.formos.stripe.service.dto.price.AdminPriceRequest;
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
public class PriceResource {

    private final Logger log = LoggerFactory.getLogger(PriceResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriceService priceService;

    public PriceResource(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/prices")
    public ResponseEntity<String> createPrice(@RequestBody AdminPriceRequest request) throws URISyntaxException {
        log.debug("REST request to save Price : {}", request);
        String response = priceService.createPrice(request);
        return ResponseEntity
            .created(new URI("/api/admin/users/prices"))
            .headers(HeaderUtil.createAlert(applicationName, "priceManagement.created", response))
            .body(response);
    }
}
