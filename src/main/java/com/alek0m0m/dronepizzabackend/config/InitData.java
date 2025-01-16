package com.alek0m0m.dronepizzabackend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alek0m0m.dronepizzabackend.domains.pizza.*;
import com.alek0m0m.dronepizzabackend.domains.station.*;
import com.alek0m0m.dronepizzabackend.domains.delivery.*;
import com.alek0m0m.dronepizzabackend.domains.drone.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class InitData {

    private final StationRepository stationRepository;
    private final PizzaRepository pizzaRepository;
    private final DroneRepository droneRepository;
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public InitData(StationRepository stationRepository, 
                   PizzaRepository pizzaRepository,
                   DroneRepository droneRepository,
                   DeliveryRepository deliveryRepository) {
        this.stationRepository = stationRepository;
        this.pizzaRepository = pizzaRepository;
        this.droneRepository = droneRepository;
        this.deliveryRepository = deliveryRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("---------------------------------");
        System.out.println("Initializing data...");

        initializeStations();
        initializePizzas();
        initializeDrones();
        initializeDeliveries();

        System.out.println();
        System.out.println("... data initialized");
        System.out.println("---------------------------------");
    }

    private void initializeStations() {
        if (stationRepository.count() == 0) {
            Station station1 = new Station();
            station1.setLatitude(55.41);  // Copenhagen center
            station1.setLongitude(12.34);
            stationRepository.save(station1);

            Station station2 = new Station();
            station2.setLatitude(55.43);  // North Copenhagen
            station2.setLongitude(12.33);
            stationRepository.save(station2);

            Station station3 = new Station();
            station3.setLatitude(55.40);  // South Copenhagen
            station3.setLongitude(12.35);
            stationRepository.save(station3);
        }
        System.out.println(" Stations initialized");
    }

    private void initializePizzas() {
        if (pizzaRepository.count() == 0) {
            Pizza margherita = new Pizza();
            margherita.setTitle("Margherita");
            margherita.setPriceInDKK(85);
            pizzaRepository.save(margherita);

            Pizza pepperoni = new Pizza();
            pepperoni.setTitle("Pepperoni");
            pepperoni.setPriceInDKK(95);
            pizzaRepository.save(pepperoni);

            Pizza hawaii = new Pizza();
            hawaii.setTitle("Hawaii");
            hawaii.setPriceInDKK(90);
            pizzaRepository.save(hawaii);

            Pizza vesuvio = new Pizza();
            vesuvio.setTitle("Vesuvio");
            vesuvio.setPriceInDKK(89);
            pizzaRepository.save(vesuvio);

            Pizza calzone = new Pizza();
            calzone.setTitle("Calzone");
            calzone.setPriceInDKK(99);
            pizzaRepository.save(calzone);
        }
        System.out.println(" Pizzas initialized");
    }


    private void initializeDrones() {
        if (droneRepository.count() == 0) {
            // Get all stations
            var stations = stationRepository.findAll();
            
            // Drone in operation at station 1
            Drone drone1 = new Drone();
            drone1.setSerialNumber(UUID.randomUUID());
            drone1.setStatus(Drone.DroneStatus.I_DRIFT);
            drone1.setStation(stations.get(0));
            droneRepository.save(drone1);

            // Drone out of operation at station 2
            Drone drone2 = new Drone();
            drone2.setSerialNumber(UUID.randomUUID());
            drone2.setStatus(Drone.DroneStatus.UDE_AF_DRIFT);
            drone2.setStation(stations.get(1));
            droneRepository.save(drone2);

            // Retired drone at station 3
            Drone drone3 = new Drone();
            drone3.setSerialNumber(UUID.randomUUID());
            drone3.setStatus(Drone.DroneStatus.UDFASET);
            drone3.setStation(stations.get(2));
            droneRepository.save(drone3);

            // Two more operational drones
            Drone drone4 = new Drone();
            drone4.setSerialNumber(UUID.randomUUID());
            drone4.setStatus(Drone.DroneStatus.I_DRIFT);
            drone4.setStation(stations.get(0));
            droneRepository.save(drone4);

            Drone drone5 = new Drone();
            drone5.setSerialNumber(UUID.randomUUID());
            drone5.setStatus(Drone.DroneStatus.I_DRIFT);
            drone5.setStation(stations.get(1));
            droneRepository.save(drone5);
        }

        System.out.println(" Drones initialized");
    }

    private void initializeDeliveries() {
        if (deliveryRepository.count() == 0) {
            var pizzas = pizzaRepository.findAll();
            var drones = droneRepository.findAll();
            var operationalDrones = drones.stream()
                .filter(d -> Drone.DroneStatus.I_DRIFT.equals(d.getStatus()))
                .toList();


            // Delivery waiting for drone assignment
            Delivery delivery1 = new Delivery();
            delivery1.setPizza(pizzas.get(0));
            delivery1.setDeliveryAddress("Nørrebrogade 1, 2200 København");
            delivery1.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
            deliveryRepository.save(delivery1);

            // Delivery in progress with drone
            Delivery delivery2 = new Delivery();
            delivery2.setPizza(pizzas.get(1));
            delivery2.setDeliveryAddress("Vesterbrogade 20, 1620 København");
            delivery2.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(15));
            delivery2.setDrone(operationalDrones.get(0));
            deliveryRepository.save(delivery2);

            // Another delivery waiting for drone
            Delivery delivery3 = new Delivery();
            delivery3.setPizza(pizzas.get(2));
            delivery3.setDeliveryAddress("Østerbrogade 15, 2100 København");
            delivery3.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(45));
            deliveryRepository.save(delivery3);

            // Delivery in progress with different drone
            Delivery delivery4 = new Delivery();
            delivery4.setPizza(pizzas.get(3));
            delivery4.setDeliveryAddress("Amagerbrogade 25, 2300 København");
            delivery4.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(20));
            delivery4.setDrone(operationalDrones.get(1));
            deliveryRepository.save(delivery4);

            // Older delivery waiting for drone (to test sorting)
            Delivery delivery5 = new Delivery();
            delivery5.setPizza(pizzas.get(4));
            delivery5.setDeliveryAddress("Frederikssundsvej 10, 2400 København");
            delivery5.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(5));
            deliveryRepository.save(delivery5);
        }

        System.out.println(" Deliveries initialized");
    }
}
