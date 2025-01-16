package com.alek0m0m.dronepizzabackend.entities_test;

import com.alek0m0m.dronepizzabackend.TestDatabaseHelper;
import com.alek0m0m.dronepizzabackend.domains.delivery.Delivery;
import com.alek0m0m.dronepizzabackend.domains.delivery.DeliveryRepository;
import com.alek0m0m.dronepizzabackend.domains.drone.Drone;
import com.alek0m0m.dronepizzabackend.domains.drone.DroneRepository;
import com.alek0m0m.dronepizzabackend.domains.pizza.Pizza;
import com.alek0m0m.dronepizzabackend.domains.pizza.PizzaRepository;
import com.alek0m0m.dronepizzabackend.domains.station.Station;
import com.alek0m0m.dronepizzabackend.domains.station.StationRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;

// DroneTest.java
@SpringBootTest
class DroneTest {
    @Autowired
    private DroneRepository droneRepository;
    
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TestDatabaseHelper dbHelper;
    private TestDatabaseHelper.TestEntities testEntities;

    private Station testStation;
    private Drone testDrone;

    @BeforeEach
    void setUp() {
        testEntities = new TestDatabaseHelper.TestEntities();

        // Create a test station
        testStation = new Station();
        testStation.setLatitude(55.41);
        testStation.setLongitude(12.34);
        testStation = stationRepository.save(testStation);

        // Create a test drone
        testDrone = new Drone();
        testDrone.setSerialNumber(UUID.randomUUID());
        testDrone.setStatus(Drone.DroneStatus.I_DRIFT);
        testDrone.setStation(testStation);
        testDrone = droneRepository.save(testDrone);

        testEntities.withDrone(testDrone.getId());
        testEntities.withStation(testStation.getId());
    }

    @AfterEach
    void tearDown() {
        dbHelper.cleanupTestEntities(testEntities);
    }

    @Test
    void testCreateDrone() {
        Drone savedDrone = droneRepository.save(testDrone);

        assertNotNull(savedDrone.getId());
        assertEquals(testDrone.getSerialNumber(), savedDrone.getSerialNumber());
        assertEquals(testDrone.getStatus(), savedDrone.getStatus());
        assertEquals(testStation.getId(), savedDrone.getStation().getId());

        testEntities.withDrone(savedDrone.getId());
    }

    @Test
    void testUpdateDroneStatus() {
        // arrange
        Drone savedDrone = droneRepository.save(testDrone);

        // act
        savedDrone.setStatus(Drone.DroneStatus.UDE_AF_DRIFT);
        Drone updatedDrone = droneRepository.save(savedDrone);

        // assert
        assertEquals(Drone.DroneStatus.UDE_AF_DRIFT, updatedDrone.getStatus());

        testEntities.withDrone(updatedDrone.getId());
    }

    @Test
    void testDroneStationRelationship() {
        Station station = new Station()
            .setLatitude(55.41)
            .setLongitude(12.34);
        station = stationRepository.save(station);


        Drone drone = new Drone()
            .setSerialNumber(UUID.randomUUID())
            .setStatus(Drone.DroneStatus.I_DRIFT);

        // Add drone to station: establishes connection
            // adds to drones of station and calls drone.setStation(station)
        station.addDrone(drone);
        drone = droneRepository.save(drone);

        // even with EAGER loading, we need a fresh copy after save
        station = stationRepository.findById(station.getId()).orElseThrow();

        assertEquals(station.getId(), drone.getStation().getId());
        assertEquals(station.getDrones().get(0).getId(), drone.getId());
        assertTrue(drone.getStation().getDrones().contains(drone));
        assertTrue(station.getDrones().contains(drone));


        testEntities.withDrone(drone.getId());
        testEntities.withStation(station.getId());
    }
}

// StationTest.java
@SpringBootTest
class StationTest {
    @Autowired
    private StationRepository stationRepository;

    private Station testStation;

    @Autowired
    private TestDatabaseHelper dbHelper;
    private TestDatabaseHelper.TestEntities testEntities;

    @BeforeEach
    void setUp() {
        testEntities = new TestDatabaseHelper.TestEntities();

        testStation = new Station();
        testStation.setLatitude(55.41);
        testStation.setLongitude(12.34);
    }

    @AfterEach
    void tearDown() {
        dbHelper.cleanupTestEntities(testEntities);
    }

    @Test
    void testCreateStation() {
        Station savedStation = stationRepository.save(testStation);
        assertNotNull(savedStation.getId());
        assertEquals(testStation.getLatitude(), savedStation.getLatitude());
        assertEquals(testStation.getLongitude(), savedStation.getLongitude());

        testEntities.withStation(savedStation.getId());
    }

    @Test
    void testStationCoordinates() {
        Station savedStation = stationRepository.save(testStation);
        assertEquals(55.41, savedStation.getLatitude(), 0.001);
        assertEquals(12.34, savedStation.getLongitude(), 0.001);

        testEntities.withStation(savedStation.getId());
    }
}

// PizzaTest.java
@SpringBootTest
class PizzaTest {

    @Autowired
    private TestDatabaseHelper dbHelper;
    private TestDatabaseHelper.TestEntities testEntities;

    @Autowired
    private PizzaRepository pizzaRepository;

    private Pizza testPizza;

    @BeforeEach
    void setUp() {
        testEntities = new TestDatabaseHelper.TestEntities();

        testPizza = new Pizza();
        testPizza.setTitle("Margherita");
        testPizza.setPriceInDKK(85);
    }

    @AfterEach
    void tearDown() {
        dbHelper.cleanupTestEntities(testEntities);
    }


    @Test
    void testCreatePizza() {
        Pizza savedPizza = pizzaRepository.save(testPizza);
        assertNotNull(savedPizza.getId());
        assertEquals(testPizza.getTitle(), savedPizza.getTitle());
        assertEquals(testPizza.getPriceInDKK(), savedPizza.getPriceInDKK());

        testEntities.withPizza(savedPizza.getId());
    }

    @Test
    void testPizzaPrice() {
        Pizza savedPizza = pizzaRepository.save(testPizza);
        assertTrue(savedPizza.getPriceInDKK() > 0);
        assertEquals(Integer.class, savedPizza.getPriceInDKK().getClass());

        testEntities.withPizza(savedPizza.getId());
    }
}

// DeliveryTest.java
@SpringBootTest
class DeliveryTest {
    @Autowired
    private TestDatabaseHelper dbHelper;

    @Autowired
    private DeliveryRepository deliveryRepository;
    
    @Autowired
    private DroneRepository droneRepository;
    
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private StationRepository stationRepository;

    private TestDatabaseHelper.TestEntities testEntities;

    private Delivery testDelivery;
    private Station testStation;
    private Pizza testPizza;
    private Drone testDrone;

    @BeforeEach
    void setUp() {
        testEntities = new TestDatabaseHelper.TestEntities();

        // Create test pizza
        testPizza = new Pizza();
        testPizza.setTitle("Margherita");
        testPizza.setPriceInDKK(85);
        Pizza savedPizza = pizzaRepository.save(testPizza);
        testEntities.withPizza(savedPizza.getId());

        testStation = new Station();
        testStation.setLatitude(55.41);
        testStation.setLongitude(12.34);
        stationRepository.save(testStation);
        testEntities.withStation(testStation.getId());

        testDrone = new Drone();
        testDrone.setSerialNumber(UUID.randomUUID());
        testDrone.setStatus(Drone.DroneStatus.I_DRIFT);
        testDrone.setStation(testStation);
        droneRepository.save(testDrone);
        testEntities.withDrone(testDrone.getId());

        // Create test delivery
        testDelivery = new Delivery();
        testDelivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
        testDelivery.setDeliveryAddress("Guldbergsgade 29N, 2200 København");
        testDelivery.setPizza(testPizza);
        deliveryRepository.save(testDelivery);
        testEntities.withDelivery(testDelivery.getId());
    }

    @AfterEach
    void tearDown() {
        dbHelper.cleanupTestEntities(testEntities);
    }

    @Test
    void testCreateDelivery() {
        Delivery savedDelivery = deliveryRepository.save(testDelivery);

        assertNotNull(savedDelivery.getId());
        assertEquals(testDelivery.getExpectedDeliveryTime(), savedDelivery.getExpectedDeliveryTime());
        assertEquals(testDelivery.getDeliveryAddress(), savedDelivery.getDeliveryAddress());
        assertNull(savedDelivery.getActualDeliveryTime());

        testEntities.withDelivery(savedDelivery.getId());
    }

    @Test
    void testAssignDroneToDelivery() {
        testDelivery.setDrone(testDrone);
        Delivery savedDelivery = deliveryRepository.save(testDelivery);
        assertNotNull(savedDelivery.getDrone());
        assertEquals(testDrone.getId(), savedDelivery.getDrone().getId());

        testEntities.withDelivery(savedDelivery.getId());
    }

    @Test
    void testCompleteDelivery() {
        Delivery savedDelivery = deliveryRepository.save(testDelivery);

        LocalDateTime completionTime = LocalDateTime.now();
        savedDelivery.setActualDeliveryTime(completionTime);
        Delivery completedDelivery = deliveryRepository.save(savedDelivery);
        assertNotNull(completedDelivery.getActualDeliveryTime());

        testEntities.withDelivery(completedDelivery.getId());
    }
}
