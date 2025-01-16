
package com.alek0m0m.dronepizzabackend.services_tests;

import com.alek0m0m.dronepizzabackend.delivery.*;
import com.alek0m0m.dronepizzabackend.drone.*;
import com.alek0m0m.dronepizzabackend.pizza.*;
import com.alek0m0m.dronepizzabackend.station.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DroneServiceTest {
    @Mock
    private DroneRepository droneRepository;
    
    @Mock
    private StationRepository stationRepository;
    
    @InjectMocks
    private DroneService droneService;
    
    private Station testStation;
    private Drone testDrone;
    
    @BeforeEach
    void setUp() {
        testStation = new Station();
        testStation.setId(1L);
        testStation.setLatitude(55.41);
        testStation.setLongitude(12.34);
        
        testDrone = new Drone();
        testDrone.setId(1L);
        testDrone.setSerialNumber(UUID.randomUUID());
        testDrone.setStatus(Drone.DroneStatus.I_DRIFT);
        testDrone.setStation(testStation);
    }
    
    @Test
    void getAllDrones_ShouldReturnDronesAsDTOs() {
        // Arrange
        when(droneRepository.findAll()).thenReturn(List.of(testDrone));
        
        // Act
        List<DroneDTO> result = droneService.getAllDrones();
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDrone.getSerialNumber(), result.get(0).getSerialNumber());
        assertEquals(testDrone.getStatus().toString(), result.get(0).getStatus());
    }
    
    @Test
    void addNewDrone_ShouldCreateDroneAtLeastLoadedStation() {
        // Arrange
        when(stationRepository.findAllOrderByDroneCount()).thenReturn(List.of(testStation));
        when(droneRepository.save(any(Drone.class))).thenAnswer(invocation -> {
            Drone savedDrone = invocation.getArgument(0);
            savedDrone.setId(1L);
            return savedDrone;
        });
        
        // Act
        DroneDTO result = droneService.addNewDrone();
        
        // Assert
        assertNotNull(result);
        assertEquals(Drone.DroneStatus.I_DRIFT.toString(), result.getStatus());
        assertEquals(testStation.getId(), result.getStation().getId());
        verify(droneRepository).save(any(Drone.class));
    }
    
    @Test
    void addNewDrone_ShouldThrowException_WhenNoStationsAvailable() {
        // Arrange
        when(stationRepository.findAllOrderByDroneCount()).thenReturn(List.of());
        
        // Act & Assert
        assertThrows(NoStationAvailableException.class, () -> droneService.addNewDrone());
    }
    
    @Test
    void enableDrone_ShouldChangeStatusToOperational() {
        // Arrange
        UUID serialNumber = testDrone.getSerialNumber();
        when(droneRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.of(testDrone));
        when(droneRepository.save(any(Drone.class))).thenReturn(testDrone);
        
        // Act
        DroneDTO result = droneService.enableDrone(serialNumber);
        
        // Assert
        assertEquals(Drone.DroneStatus.I_DRIFT.toString(), result.getStatus());
        verify(droneRepository).save(testDrone);
    }
}


@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {
    @Mock
    private DeliveryRepository deliveryRepository;
    
    @Mock
    private DroneRepository droneRepository;
    
    @Mock
    private PizzaRepository pizzaRepository;
    
    @InjectMocks
    private DeliveryService deliveryService;
    
    private Pizza testPizza;
    private Drone testDrone;
    private Delivery testDelivery;
    
    @BeforeEach
    void setUp() {
        testPizza = new Pizza();
        testPizza.setId(1L);
        testPizza.setTitle("Margherita");
        testPizza.setPriceInDKK(85);
        
        Station testStation = new Station();
        testStation.setId(1L);
        testStation.setLatitude(55.41);
        testStation.setLongitude(12.34);
        
        testDrone = new Drone();
        testDrone.setId(1L);
        testDrone.setSerialNumber(UUID.randomUUID());
        testDrone.setStatus(Drone.DroneStatus.I_DRIFT);
        testDrone.setStation(testStation);
        
        testDelivery = new Delivery();
        testDelivery.setId(1L);
        testDelivery.setPizza(testPizza);
        testDelivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
        testDelivery.setDeliveryAddress("Test Address");
    }
    
    @Test
    void getUndeliveredOrders_ShouldReturnUndeliveredOrdersAsDTOs() {
        // Arrange
        when(deliveryRepository.findByActualDeliveryTimeIsNullOrderByExpectedDeliveryTimeAsc())
            .thenReturn(List.of(testDelivery));
            
        // Act
        List<DeliveryDTO> result = deliveryService.getUndeliveredOrders();
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDelivery.getId(), result.get(0).getId());
        assertNull(result.get(0).getActualDeliveryTime());
    }
    
    @Test
    void createDelivery_ShouldCreateNewDelivery() {
        // Arrange
        when(pizzaRepository.findById(testPizza.getId())).thenReturn(Optional.of(testPizza));
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(invocation -> {
            Delivery savedDelivery = invocation.getArgument(0);
            savedDelivery.setId(1L);
            return savedDelivery;
        });
        
        // Act
        DeliveryDTO result = deliveryService.createDelivery(testPizza.getId());
        
        // Assert
        assertNotNull(result);
        assertEquals(testPizza.getId(), result.getPizza().getId());
        assertTrue(result.getExpectedDeliveryTime().isAfter(LocalDateTime.now()));
        verify(deliveryRepository).save(any(Delivery.class));
    }
    
    @Test
    void scheduleDelivery_ShouldAssignDroneToDelivery() {
        // Arrange
        UUID droneSerial = testDrone.getSerialNumber();
        when(deliveryRepository.findById(testDelivery.getId())).thenReturn(Optional.of(testDelivery));
        when(droneRepository.findBySerialNumber(droneSerial)).thenReturn(Optional.of(testDrone));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(testDelivery);
        
        // Act
        DeliveryDTO result = deliveryService.scheduleDelivery(testDelivery.getId(), droneSerial);
        
        // Assert
        assertNotNull(result);
        verify(deliveryRepository).save(any(Delivery.class));
    }
    
    @Test
    void finishDelivery_ShouldSetActualDeliveryTime() {
        // Arrange
        testDelivery.setDrone(testDrone);
        when(deliveryRepository.findById(testDelivery.getId())).thenReturn(Optional.of(testDelivery));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(testDelivery);
        
        // Act
        DeliveryDTO result = deliveryService.finishDelivery(testDelivery.getId());
        
        // Assert
        assertNotNull(result);
        assertNotNull(result.getActualDeliveryTime());
        verify(deliveryRepository).save(any(Delivery.class));
    }
    
    @Test
    void scheduleDeliveryWithRandomDrone_ShouldAssignRandomOperationalDrone() {
        // Arrange
        when(deliveryRepository.findById(testDelivery.getId())).thenReturn(Optional.of(testDelivery));
        when(droneRepository.findByStatus(Drone.DroneStatus.I_DRIFT)).thenReturn(List.of(testDrone));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(testDelivery);
        
        // Act
        DeliveryDTO result = deliveryService.scheduleDeliveryWithRandomDrone(testDelivery.getId());
        
        // Assert
        assertNotNull(result);
        verify(deliveryRepository).save(any(Delivery.class));
    }
}
