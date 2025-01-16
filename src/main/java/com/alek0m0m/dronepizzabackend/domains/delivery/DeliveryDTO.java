package com.alek0m0m.dronepizzabackend.domains.delivery;

import com.alek0m0m.dronepizzabackend.domains.drone.DroneDTO;
import com.alek0m0m.dronepizzabackend.domains.pizza.PizzaDTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class DeliveryDTO {
    private Long id;
    private LocalDateTime expectedDeliveryTime;
    private LocalDateTime actualDeliveryTime;
    private String deliveryAddress;
    private PizzaDTO pizza;
    private DroneDTO drone;

    public DeliveryDTO() {
    }

    public DeliveryDTO(Long id, LocalDateTime expectedDeliveryTime, LocalDateTime actualDeliveryTime, 
                      String deliveryAddress, PizzaDTO pizza, DroneDTO drone) {
        this.id = id;
        this.expectedDeliveryTime = expectedDeliveryTime;
        this.actualDeliveryTime = actualDeliveryTime;
        this.deliveryAddress = deliveryAddress;
        this.pizza = pizza;
        this.drone = drone;
    }

    public Long getId() {
        return id;
    }

    public DeliveryDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public DeliveryDTO setExpectedDeliveryTime(LocalDateTime expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
        return this;
    }

    public LocalDateTime getActualDeliveryTime() {
        return actualDeliveryTime;
    }

    public DeliveryDTO setActualDeliveryTime(LocalDateTime actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime;
        return this;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public DeliveryDTO setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public PizzaDTO getPizza() {
        return pizza;
    }

    public DeliveryDTO setPizza(PizzaDTO pizza) {
        this.pizza = pizza;
        return this;
    }

    public DroneDTO getDrone() {
        return drone;
    }

    public DeliveryDTO setDrone(DroneDTO drone) {
        this.drone = drone;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryDTO that = (DeliveryDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(expectedDeliveryTime, that.expectedDeliveryTime) &&
                Objects.equals(actualDeliveryTime, that.actualDeliveryTime) &&
                Objects.equals(deliveryAddress, that.deliveryAddress) &&
                Objects.equals(pizza, that.pizza) &&
                Objects.equals(drone, that.drone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expectedDeliveryTime, actualDeliveryTime, deliveryAddress, pizza, drone);
    }

    @Override
    public String toString() {
        return "DeliveryDTO{" +
                "id=" + id +
                ", expectedDeliveryTime=" + expectedDeliveryTime +
                ", actualDeliveryTime=" + actualDeliveryTime +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", pizza=" + pizza +
                ", drone=" + drone +
                '}';
    }
}