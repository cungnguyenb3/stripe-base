package com.formos.stripe.domain;

import javax.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A recurring.
 */
@Entity
@Table(name = "recurring")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Recurring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
