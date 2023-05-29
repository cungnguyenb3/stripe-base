package com.formos.stripe.web.rest;

import com.formos.stripe.domain.User;
import com.formos.stripe.service.ProductService;
import com.formos.stripe.service.dto.AdminUserDTO;
import com.formos.stripe.service.dto.product.AdminProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/admin")
public class ProductResource {
    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService productService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<String> createUser(@RequestBody AdminProductRequest request) throws URISyntaxException {
        log.debug("REST request to save User : {}", request);
        String response = productService.createProduct(request);
        return ResponseEntity
            .created(new URI("/api/admin/users/products"))
            .headers(HeaderUtil.createAlert(applicationName, "productManagement.created", response))
            .body(response);
    }
}
