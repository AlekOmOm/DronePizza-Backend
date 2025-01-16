package com.alek0m0m.dronepizzabackend.drone;

// Custom Exceptions
public class NoStationAvailableException extends RuntimeException {
    public NoStationAvailableException(String message) {
        super(message);
    }
}
