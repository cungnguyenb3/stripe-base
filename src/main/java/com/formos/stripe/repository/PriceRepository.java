package com.formos.stripe.repository;

import com.formos.stripe.domain.Price;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, String> {
    Optional<Price> findFirstByProductId(String productId);
}
