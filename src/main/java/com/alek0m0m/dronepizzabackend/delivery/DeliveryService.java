package com.alek0m0m.dronepizzabackend.delivery;

import com.alek0m0m.dronepizzabackend.drone.*;
import com.alek0m0m.dronepizzabackend.pizza.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



@Service
@Transactional
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DroneRepository droneRepository;
    private final PizzaRepository pizzaRepository;
    private final Random random = new Random();

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository,
                           DroneRepository droneRepository,
                           PizzaRepository pizzaRepository) {
        this.deliveryRepository = deliveryRepository;
        this.droneRepository = droneRepository;
        this.pizzaRepository = pizzaRepository;
    }

    public List<DeliveryDTO> getUndeliveredOrders() {
        return deliveryRepository.findByActualDeliveryTimeIsNullOrderByExpectedDeliveryTimeAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DeliveryDTO createDelivery(Long pizzaId) {
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new PizzaNotFoundException("Pizza not found: " + pizzaId));

        Delivery delivery = new Delivery();
        delivery.setPizza(pizza);
        delivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
        delivery.setDeliveryAddress(""); // Address should come from request

        return convertToDTO(deliveryRepository.save(delivery));
    }

    public List<DeliveryDTO> getUnassignedDeliveries() {
        return deliveryRepository.findByDroneIsNullOrderByExpectedDeliveryTimeAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DeliveryDTO scheduleDelivery(Long deliveryId, UUID droneSerialNumber) {
        Delivery delivery = getUnscheduledDelivery(deliveryId);
        Drone drone = getDroneForDelivery(droneSerialNumber);

        delivery.setDrone(drone);
        return convertToDTO(deliveryRepository.save(delivery));
    }

    public DeliveryDTO scheduleDeliveryWithRandomDrone(Long deliveryId) {
        Delivery delivery = getUnscheduledDelivery(deliveryId);

        List<Drone> availableDrones = droneRepository.findByStatus(Drone.DroneStatus.I_DRIFT);
        if (availableDrones.isEmpty()) {
            throw new DroneNotOperationalException("No operational drones available");
        }

        Drone randomDrone = availableDrones.get(random.nextInt(availableDrones.size()));
        delivery.setDrone(randomDrone);

        return convertToDTO(deliveryRepository.save(delivery));
    }

    public DeliveryDTO finishDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found: " + deliveryId));

        if (delivery.getDrone() == null) {
            throw new DeliveryNotScheduledException("Delivery not scheduled: " + deliveryId);
        }

        delivery.setActualDeliveryTime(LocalDateTime.now());
        return convertToDTO(deliveryRepository.save(delivery));
    }

    private Delivery getUnscheduledDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found: " + deliveryId));

        if (delivery.getDrone() != null) {
            throw new DeliveryAlreadyScheduledException("Delivery already has a drone: " + deliveryId);
        }

        return delivery;
    }

    private Drone getDroneForDelivery(UUID serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new DroneNotFoundException("Drone not found: " + serialNumber));

        if (drone.getStatus() != Drone.DroneStatus.I_DRIFT) {
            throw new DroneNotOperationalException("Drone not operational: " + serialNumber);
        }

        return drone;
    }

    private DeliveryDTO convertToDTO(Delivery delivery) {
        DeliveryDTO dto = new DeliveryDTO();
        dto.setId(delivery.getId());
        dto.setExpectedDeliveryTime(delivery.getExpectedDeliveryTime());
        dto.setActualDeliveryTime(delivery.getActualDeliveryTime());
        dto.setDeliveryAddress(delivery.getDeliveryAddress());
        dto.setPizza(convertToDTO(delivery.getPizza()));
        if (delivery.getDrone() != null) {
            dto.setDrone(convertToDTO(delivery.getDrone()));
        }
        return dto;
    }

    private PizzaDTO convertToDTO(Pizza pizza) {
        PizzaDTO dto = new PizzaDTO();
        dto.setId(pizza.getId());
        dto.setTitle(pizza.getTitle());
        dto.setPriceInDKK(pizza.getPriceInDKK());
        return dto;
    }

    private DroneDTO convertToDTO(Drone drone) {
        DroneDTO dto = new DroneDTO();
        dto.setSerialNumber(drone.getSerialNumber());
        dto.setStatus(drone.getStatus().toString());
        return dto;
    }
}
