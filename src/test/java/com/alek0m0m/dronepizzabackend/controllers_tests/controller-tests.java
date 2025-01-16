package com.alek0m0m.dronepizzabackend.controllers_tests;

import com.alek0m0m.dronepizzabackend.domains.delivery.*;
import com.alek0m0m.dronepizzabackend.domains.drone.*;
import com.alek0m0m.dronepizzabackend.domains.pizza.*;
import com.alek0m0m.dronepizzabackend.domains.delivery.DeliveryController;
import com.alek0m0m.dronepizzabackend.domains.delivery.DeliveryDTO;
import com.alek0m0m.dronepizzabackend.domains.delivery.DeliveryNotFoundException;
import com.alek0m0m.dronepizzabackend.domains.delivery.DeliveryService;
import com.alek0m0m.dronepizzabackend.domains.drone.DroneController;
import com.alek0m0m.dronepizzabackend.domains.drone.DroneDTO;
import com.alek0m0m.dronepizzabackend.domains.drone.DroneService;
import com.alek0m0m.dronepizzabackend.domains.drone.NoStationAvailableException;
import com.alek0m0m.dronepizzabackend.domains.pizza.PizzaDTO;

import com.alek0m0m.dronepizzabackend.domains.station.StationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

// MockMVC
    // request builders
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

    // result matchers
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// Security from Security configuration
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;


@WebMvcTest(DroneController.class)
class DroneControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private DroneService droneService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private DroneDTO testDroneDTO;
    private UUID testSerialNumber;
    
    @BeforeEach
    void setUp() {
        testSerialNumber = UUID.randomUUID();
        
        StationDTO stationDTO = new StationDTO();
        stationDTO.setId(1L);
        stationDTO.setLatitude(55.41);
        stationDTO.setLongitude(12.34);
        
        testDroneDTO = new DroneDTO();
        testDroneDTO.setSerialNumber(testSerialNumber);
        testDroneDTO.setStatus("I_DRIFT");
        testDroneDTO.setStation(stationDTO);
    }
    
    @Test
    void getAllDrones_ShouldReturnDronesList() throws Exception {
        // Arrange
        when(droneService.getAllDrones()).thenReturn(List.of(testDroneDTO));
        
        // Act & Assert
        mockMvc.perform(get("/drones")
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].serialNumber").value(testSerialNumber.toString()))
            .andExpect(jsonPath("$[0].status").value("I_DRIFT"));
    }
    
    @Test
    void addDrone_ShouldReturnNewDrone() throws Exception {
        // Arrange
        when(droneService.addNewDrone()).thenReturn(testDroneDTO);
        
        // Act & Assert
        mockMvc.perform(post("/drones/add")
                .with(csrf()) // csrf token needed for POST requests
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.serialNumber").value(testSerialNumber.toString()))
            .andExpect(jsonPath("$.status").value("I_DRIFT"));
    }
    
    @Test
    void addDrone_ShouldReturnError_WhenNoStationsAvailable() throws Exception {
        // Arrange
        when(droneService.addNewDrone()).thenThrow(new NoStationAvailableException("No stations"));
        
        // Act & Assert
        mockMvc.perform(post("/drones/add")
                .with(csrf())
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isPreconditionFailed());
    }
    
    @Test
    void enableDrone_ShouldEnableDrone() throws Exception {
        // Arrange
        when(droneService.enableDrone(testSerialNumber)).thenReturn(testDroneDTO);
        
        // Act & Assert
        mockMvc.perform(post("/drones/enable")
                .param("serialNumber", testSerialNumber.toString())
                .with(csrf())
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("I_DRIFT"));
    }
}

// DeliveryControllerTest.java
@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private DeliveryService deliveryService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private DeliveryDTO testDeliveryDTO;
    private UUID testDroneSerialNumber;
    
    @BeforeEach
    void setUp() {
        testDroneSerialNumber = UUID.randomUUID();
        
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setId(1L);
        pizzaDTO.setTitle("Margherita");
        pizzaDTO.setPriceInDKK(85);
        
        DroneDTO droneDTO = new DroneDTO();
        droneDTO.setSerialNumber(testDroneSerialNumber);
        droneDTO.setStatus("I_DRIFT");
        
        testDeliveryDTO = new DeliveryDTO();
        testDeliveryDTO.setId(1L);
        testDeliveryDTO.setPizza(pizzaDTO);
        testDeliveryDTO.setDrone(droneDTO);
        testDeliveryDTO.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
        testDeliveryDTO.setDeliveryAddress("Test Address");
    }
    
    @Test
    void getUndeliveredOrders_ShouldReturnOrdersList() throws Exception {
        // Arrange
        when(deliveryService.getUndeliveredOrders()).thenReturn(List.of(testDeliveryDTO));
        
        // Act & Assert
        mockMvc.perform(get("/deliveries")
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].pizza.title").value("Margherita"));
    }
    
    @Test
    void addDelivery_ShouldCreateNewDelivery() throws Exception {
        // Arrange
        when(deliveryService.createDelivery(1L)).thenReturn(testDeliveryDTO);
        
        // Act & Assert
        mockMvc.perform(post("/deliveries/add")
                .param("pizzaId", "1")
                .with(csrf())
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.pizza.title").value("Margherita"));
    }
    
    @Test
    void scheduleDelivery_ShouldAssignDrone() throws Exception {
        // Arrange
        when(deliveryService.scheduleDelivery(1L, testDroneSerialNumber)).thenReturn(testDeliveryDTO);
        
        // Act & Assert
        mockMvc.perform(post("/deliveries/schedule")
                .param("deliveryId", "1")
                .param("droneSerialNumber", testDroneSerialNumber.toString())
                .with(csrf())
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.drone.serialNumber").value(testDroneSerialNumber.toString()));
    }
    
    @Test
    void scheduleDelivery_ShouldAssignRandomDrone_WhenNodroneSpecified() throws Exception {
        // Arrange
        when(deliveryService.scheduleDeliveryWithRandomDrone(1L)).thenReturn(testDeliveryDTO);
        
        // Act & Assert
        mockMvc.perform(post("/deliveries/schedule")
                .param("deliveryId", "1")
                .with(csrf())
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.drone").exists());
    }
    
    @Test
    void finishDelivery_ShouldCompleteDelivery() throws Exception {
        // Arrange
        testDeliveryDTO.setActualDeliveryTime(LocalDateTime.now());
        when(deliveryService.finishDelivery(1L)).thenReturn(testDeliveryDTO);
        
        // Act & Assert
        mockMvc.perform(post("/deliveries/finish")
                .param("deliveryId", "1")
                .with(csrf())
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.actualDeliveryTime").exists());
    }
    
    @Test
    void finishDelivery_ShouldReturnError_WhenDeliveryNotFound() throws Exception {
        // Arrange
        when(deliveryService.finishDelivery(999L))
            .thenThrow(new DeliveryNotFoundException("Not found"));
        
        // Act & Assert
        mockMvc.perform(post("/deliveries/finish")
                .param("deliveryId", "999")
                .with(csrf())
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }

    // test for checking the exception thrown of the Controller method
            // expected = DeliveryNotScheduledException.class
                // == 409 status code, which means state conflict
    @Test
    void finishDelivery_ShouldFail_WhenNoDroneAssigned() throws Exception {
        // Arrange
        when(deliveryService.finishDelivery(1L))
                .thenThrow(new DeliveryNotScheduledException("Delivery not scheduled"));

        // Act & Assert
        mockMvc.perform(post("/deliveries/finish")
                        .param("deliveryId", "1")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isConflict()); // 409
    }

//    @Test
//    void finishDelivery_ShouldFail_WhenNoDroneAssigned_withJSON() throws Exception {
//        // Arrange
//        when(deliveryService.finishDelivery(1L))
//                .thenThrow(new DeliveryNotScheduledException("Delivery not scheduled: 1"));
//
//        // Act & Assert
//        mockMvc.perform(post("/deliveries/finish")
//                        .param("deliveryId", "1")
//                        .with(csrf())
//                        .with(user("admin").roles("ADMIN"))
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isConflict())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value("Delivery not scheduled: 1"));
//    }

    @Test
    void scheduleDelivery_ShouldMaintainConsistency_WhenAssigningDrone() throws Exception {
        // Arrange
        DroneDTO testDroneDTO = new DroneDTO()
            .setSerialNumber(UUID.randomUUID())
            .setStatus("I_DRIFT")
            .setStation(new StationDTO().setId(1L));

        DeliveryDTO beforeAssignment = new DeliveryDTO()
            .setId(1L)
            .setDrone(null);  // No drone assigned

        DeliveryDTO afterAssignment = new DeliveryDTO()
            .setId(1L)
            .setDrone(testDroneDTO);  // Drone assigned

        // Mock the service calls
        when(deliveryService.scheduleDelivery(1L, testDroneSerialNumber))
                .thenReturn(afterAssignment);

        // Act & Assert
        mockMvc.perform(post("/deliveries/schedule")
                        .param("deliveryId", "1")
                        .param("droneSerialNumber", testDroneSerialNumber.toString())
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.drone").exists())
                .andExpect(jsonPath("$.id").value(1));
    }
}
