package com.alek0m0m.dronepizzabackend.domains.drone;

public class DroneNotOperationalException extends RuntimeException {
    public DroneNotOperationalException(String message) {
        super(message);
    }
}
