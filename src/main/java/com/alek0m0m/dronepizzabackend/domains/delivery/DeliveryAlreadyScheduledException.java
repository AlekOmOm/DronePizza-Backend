package com.alek0m0m.dronepizzabackend.domains.delivery;

public class DeliveryAlreadyScheduledException extends RuntimeException {
    public DeliveryAlreadyScheduledException(String message) {
        super(message);
    }
}
