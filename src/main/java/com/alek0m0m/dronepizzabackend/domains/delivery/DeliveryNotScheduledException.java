package com.alek0m0m.dronepizzabackend.domains.delivery;

public class DeliveryNotScheduledException extends RuntimeException {
    public DeliveryNotScheduledException(String message) {
        super(message);
    }
}
