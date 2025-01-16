package com.alek0m0m.dronepizzabackend.domains.drone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/drones")
public class DroneController {
    private final DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping
    public ResponseEntity<List<DroneDTO>> getAllDrones() {
        return ResponseEntity.ok(droneService.getAllDrones());
    }

    @PostMapping("/add")
    public ResponseEntity<DroneDTO> addDrone() {
        try {
            return ResponseEntity.ok(droneService.addNewDrone());
        } catch (NoStationAvailableException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(null);
        }
    }

    @PostMapping("/enable")
    public ResponseEntity<DroneDTO> enableDrone(@RequestParam UUID serialNumber) {
        try {
            return ResponseEntity.ok(droneService.enableDrone(serialNumber));
        } catch (DroneNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/disable")
    public ResponseEntity<DroneDTO> disableDrone(@RequestParam UUID serialNumber) {
        try {
            return ResponseEntity.ok(droneService.disableDrone(serialNumber));
        } catch (DroneNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/retire")
    public ResponseEntity<DroneDTO> retireDrone(@RequestParam UUID serialNumber) {
        try {
            return ResponseEntity.ok(droneService.retireDrone(serialNumber));
        } catch (DroneNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
