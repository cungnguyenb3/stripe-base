package com.formos.stripe.domain;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * A Customer.
 */
@Entity
@Table(name = "Customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer {

    @Id
    private String id;

    @Column(name = "name", length = 255, nullable = true)
    private String name;

    @Column(name = "description", length = 255, nullable = true)
    private String description;

    @Column(name = "email", length = 255, nullable = true)
    private String email;

    @Column(name = "phone", length = 255, nullable = true)
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return (
            "Customer{" +
            "id='" +
            id +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", email='" +
            email +
            '\'' +
            ", phone='" +
            phone +
            '\'' +
            '}'
        );
    }
}
