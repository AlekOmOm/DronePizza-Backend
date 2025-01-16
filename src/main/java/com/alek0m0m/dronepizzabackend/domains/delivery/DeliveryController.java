package com.alek0m0m.dronepizzabackend.domains.delivery;


import com.alek0m0m.dronepizzabackend.domains.drone.DroneNotFoundException;
import com.alek0m0m.dronepizzabackend.domains.drone.DroneNotOperationalException;
import com.alek0m0m.dronepizzabackend.domains.pizza.PizzaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.alek0m0m.dronepizzabackend.domains.ErrorResponse;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getUndeliveredOrders() {
        return ResponseEntity.ok(deliveryService.getUndeliveredOrders());
    }

    @PostMapping("/add")
    public ResponseEntity<DeliveryDTO> addDelivery(@RequestParam Long pizzaId) {
        try {
            return ResponseEntity.ok(deliveryService.createDelivery(pizzaId));
        } catch (PizzaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/queue")
    public ResponseEntity<List<DeliveryDTO>> getUnassignedDeliveries() {
        return ResponseEntity.ok(deliveryService.getUnassignedDeliveries());
    }

    @PostMapping("/schedule")
    public ResponseEntity<DeliveryDTO> scheduleDelivery(
            @RequestParam Long deliveryId,
            @RequestParam(required = false) UUID droneSerialNumber) {
        try {
            DeliveryDTO delivery = droneSerialNumber != null ?
                    deliveryService.scheduleDelivery(deliveryId, droneSerialNumber) :
                    deliveryService.scheduleDeliveryWithRandomDrone(deliveryId);
            return ResponseEntity.ok(delivery);
        } catch (DeliveryNotFoundException | DroneNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DeliveryAlreadyScheduledException | DroneNotOperationalException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/finish")
    public ResponseEntity<DeliveryDTO> finishDelivery(@RequestParam Long deliveryId) { // returns Object, in case of error returns ErrorResponse
        try {
            return ResponseEntity.ok(deliveryService.finishDelivery(deliveryId));
        } catch (DeliveryNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DeliveryNotScheduledException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
                    //.body(new ErrorResponse(e.getMessage())); // custom ErrorResponse class to return error message
        }
    }
}
