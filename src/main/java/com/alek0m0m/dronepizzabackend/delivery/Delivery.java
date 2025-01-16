package com.alek0m0m.dronepizzabackend.delivery;


import com.alek0m0m.dronepizzabackend.drone.Drone;
import com.alek0m0m.dronepizzabackend.pizza.Pizza;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime expectedDeliveryTime;

    @Column
    private LocalDateTime actualDeliveryTime;

    @Column(nullable = false)
    private String deliveryAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drone_id")
    private Drone drone;


    // ----------------- CRUD and Getters/Setters -----------------
    // crud operations for pizza and drone

    public Delivery addPizza(Pizza pizza) {
        if (pizza != null) {
            this.pizza = pizza;
        }
        return this;
    }

    public Delivery removePizza() {
        this.pizza = null;
        return this;
    }

    public Delivery addDrone(Drone drone) {
        if (drone != null) {
            this.drone = drone;
        }
        return this;
    }

    public Delivery removeDrone() {
        this.drone = null;
        return this;
    }




    // Constructors
    public Delivery() {
    }

    public Delivery(Long id, LocalDateTime expectedDeliveryTime, LocalDateTime actualDeliveryTime, String deliveryAddress, Pizza pizza, Drone drone) {
        this.id = id;
        this.expectedDeliveryTime = expectedDeliveryTime;
        this.actualDeliveryTime = actualDeliveryTime;
        this.deliveryAddress = deliveryAddress;
        this.pizza = pizza;
        this.drone = drone;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(LocalDateTime expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public LocalDateTime getActualDeliveryTime() {
        return actualDeliveryTime;
    }

    public void setActualDeliveryTime(LocalDateTime actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }
}