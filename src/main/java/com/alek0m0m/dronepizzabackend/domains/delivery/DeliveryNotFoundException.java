package com.alek0m0m.dronepizzabackend.domains.delivery;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(String message) {
        super(message);
    }
}
