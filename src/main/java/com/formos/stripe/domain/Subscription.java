package com.formos.stripe.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * A subscription.
 */
@Entity
@Table(name = "subscription")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subscription {

    @Id
    private String id;

    @Column(name = "description", length = 255, nullable = true)
    private String description;

    @Column(name = "default_payment_method", length = 255, nullable = true)
    private String defaultPaymentMethod;

    @Column(name = "status", length = 255, nullable = true)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultPaymentMethod() {
        return defaultPaymentMethod;
    }

    public void setDefaultPaymentMethod(String defaultPaymentMethod) {
        this.defaultPaymentMethod = defaultPaymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return (
            "Subscription{" +
            "id='" +
            id +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", defaultPaymentMethod='" +
            defaultPaymentMethod +
            '\'' +
            ", status='" +
            status +
            '\'' +
            '}'
        );
    }
}
