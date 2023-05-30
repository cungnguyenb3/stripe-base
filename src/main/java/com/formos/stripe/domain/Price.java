package com.formos.stripe.domain;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * A product.
 */
@Entity
@Table(name = "Price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Price {

    @Id
    private String id;

    @Column(name = "unit_amount", length = 255, nullable = true)
    private Long unitAmount;

    @Column(name = "currency", length = 255, nullable = true)
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Long unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return (
            "Price{" + "id='" + id + '\'' + ", unitAmount=" + unitAmount + ", currency='" + currency + '\'' + ", product=" + product + '}'
        );
    }
}
