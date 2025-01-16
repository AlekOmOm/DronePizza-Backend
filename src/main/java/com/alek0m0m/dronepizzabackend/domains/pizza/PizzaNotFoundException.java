package com.alek0m0m.dronepizzabackend.domains.pizza;

public class PizzaNotFoundException extends RuntimeException {
    public PizzaNotFoundException(String message) {
        super(message);
    }
}
