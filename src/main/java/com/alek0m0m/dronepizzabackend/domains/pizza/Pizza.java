package com.alek0m0m.dronepizzabackend.domains.pizza;


import com.alek0m0m.dronepizzabackend.domains.delivery.Delivery;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer priceInDKK;

    @OneToMany(mappedBy = "pizza")
    private List<Delivery> deliveries = new ArrayList<>();

    // No-Args Constructor
    public Pizza() {
    }

    // All-Args Constructor
    public Pizza(Long id, String title, Integer priceInDKK, List<Delivery> deliveries) {
        this.id = id;
        this.title = title;
        this.priceInDKK = priceInDKK;
        this.deliveries = deliveries;
    }

    // ----------------- CRUD and Getters/Setters -----------------

    // CRUD

    public Pizza addDelivery(Delivery delivery) {
        if (delivery != null) {
            deliveries.add(delivery);
            delivery.setPizza(this);
        }
        return this;
    }

    public Pizza removeDelivery(Delivery delivery) {
        if (delivery != null) {
            deliveries.remove(delivery);
            delivery.setPizza(null);
        }
        return this;
    }

    // -----------------Getters/Setters -----------------

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPriceInDKK() {
        return priceInDKK;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriceInDKK(Integer priceInDKK) {
        this.priceInDKK = priceInDKK;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}