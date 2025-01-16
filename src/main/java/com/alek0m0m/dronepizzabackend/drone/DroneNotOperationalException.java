package com.alek0m0m.dronepizzabackend.drone;

public class DroneNotOperationalException extends RuntimeException {
    public DroneNotOperationalException(String message) {
        super(message);
    }
}
