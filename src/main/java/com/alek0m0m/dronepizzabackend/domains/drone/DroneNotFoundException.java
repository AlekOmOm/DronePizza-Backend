package com.alek0m0m.dronepizzabackend.domains.drone;

public class DroneNotFoundException extends RuntimeException {
    public DroneNotFoundException(String message) {
        super(message);
    }
}
