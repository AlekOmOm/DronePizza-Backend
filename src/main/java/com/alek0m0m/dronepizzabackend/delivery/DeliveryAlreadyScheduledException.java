package com.alek0m0m.dronepizzabackend.delivery;

public class DeliveryAlreadyScheduledException extends RuntimeException {
    public DeliveryAlreadyScheduledException(String message) {
        super(message);
    }
}
