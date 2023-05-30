package com.formos.stripe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A payment method.
 */
@Entity
@Table(name = "payment_method")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentMethod {

    @Id
    private String id;

    @Column(name = "type", length = 255, nullable = true)
    private String type;

    @Column(name = "card", columnDefinition = "TEXT", nullable = true)
    private String card;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" + "id='" + id + '\'' + ", type='" + type + '\'' + ", card='" + card + '\'' + '}';
    }
}
