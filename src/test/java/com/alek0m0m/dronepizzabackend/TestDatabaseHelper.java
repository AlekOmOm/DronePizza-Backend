package com.alek0m0m.dronepizzabackend;


import com.alek0m0m.dronepizzabackend.domains.delivery.DeliveryRepository;
import com.alek0m0m.dronepizzabackend.domains.drone.DroneRepository;
import com.alek0m0m.dronepizzabackend.domains.pizza.PizzaRepository;
import com.alek0m0m.dronepizzabackend.domains.station.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TestDatabaseHelper.java
@Component
public class TestDatabaseHelper {

    private final DeliveryRepository deliveryRepository;
    private final DroneRepository droneRepository;
    private final StationRepository stationRepository;
    private final PizzaRepository pizzaRepository;

    @Autowired
    public TestDatabaseHelper(
            DeliveryRepository deliveryRepository,
            DroneRepository droneRepository,
            StationRepository stationRepository,
            PizzaRepository pizzaRepository) {
        this.deliveryRepository = deliveryRepository;
        this.droneRepository = droneRepository;
        this.stationRepository = stationRepository;
        this.pizzaRepository = pizzaRepository;
    }

    // Method to delete a set of test entities
    public void cleanupTestEntities(TestEntities entities) {
        // Delete in correct order to handle foreign key constraints
        if (entities.deliveryId != null) {
            deliveryRepository.deleteById(entities.deliveryId);
        }

        if (entities.droneId != null) {
            droneRepository.deleteById(entities.droneId);
        }

        if (entities.stationId != null) {
            stationRepository.deleteById(entities.stationId);
        }

        if (entities.pizzaId != null) {
            pizzaRepository.deleteById(entities.pizzaId);
        }
    }

    // Helper class to hold test entity IDs
    public static class TestEntities {
        public Long deliveryId;
        public Long droneId;
        public Long stationId;
        public Long pizzaId;

        public TestEntities() {}

        // Builder methods for fluent API
        public TestEntities withDelivery(Long id) {
            this.deliveryId = id;
            return this;
        }

        public TestEntities withDrone(Long id) {
            this.droneId = id;
            return this;
        }

        public TestEntities withStation(Long id) {
            this.stationId = id;
            return this;
        }

        public TestEntities withPizza(Long id) {
            this.pizzaId = id;
            return this;
        }
    }
}
