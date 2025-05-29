package com.ecommerce.ecommerce_events.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_order")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean priority;
    private String description;

    public boolean isPriority() {
        return this.priority;
    }

    public CustomerOrder() {}

    public CustomerOrder(String description) {
        this.description = description;
    }

    // Getters e Setters
    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
