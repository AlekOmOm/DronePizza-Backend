package com.alek0m0m.dronepizzabackend.domains.drone;


import com.alek0m0m.dronepizzabackend.domains.station.Station;
import com.alek0m0m.dronepizzabackend.domains.station.StationDTO;
import com.alek0m0m.dronepizzabackend.domains.station.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
public class DroneService {
    private final DroneRepository droneRepository;
    private final StationRepository stationRepository;

    @Autowired
    public DroneService(DroneRepository droneRepository, StationRepository stationRepository) {
        this.droneRepository = droneRepository;
        this.stationRepository = stationRepository;
    }

    public List<DroneDTO> getAllDrones() {
        return droneRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public DroneDTO addNewDrone() {
        // Find station with least drones
        Station leastLoadedStation = stationRepository.findAllOrderByDroneCount()
            .stream()
            .findFirst()
            .orElseThrow(() -> new NoStationAvailableException("No stations available"));

        Drone drone = new Drone();
        drone.setSerialNumber(UUID.randomUUID());
        drone.setStatus(Drone.DroneStatus.I_DRIFT);
        drone.setStation(leastLoadedStation);

        return convertToDTO(droneRepository.save(drone));
    }

    public DroneDTO enableDrone(UUID serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
            .orElseThrow(() -> new DroneNotFoundException("Drone not found: " + serialNumber));
        
        drone.setStatus(Drone.DroneStatus.I_DRIFT);
        return convertToDTO(droneRepository.save(drone));
    }

    public DroneDTO disableDrone(UUID serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
            .orElseThrow(() -> new DroneNotFoundException("Drone not found: " + serialNumber));
        
        drone.setStatus(Drone.DroneStatus.UDE_AF_DRIFT);
        return convertToDTO(droneRepository.save(drone));
    }

    public DroneDTO retireDrone(UUID serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
            .orElseThrow(() -> new DroneNotFoundException("Drone not found: " + serialNumber));
        
        drone.setStatus(Drone.DroneStatus.UDFASET);
        return convertToDTO(droneRepository.save(drone));
    }

    private DroneDTO convertToDTO(Drone drone) {
        DroneDTO dto = new DroneDTO();
        dto.setSerialNumber(drone.getSerialNumber());
        dto.setStatus(drone.getStatus().toString());
        dto.setStation(convertToDTO(drone.getStation()));
        return dto;
    }

    private StationDTO convertToDTO(Station station) {
        StationDTO dto = new StationDTO();
        dto.setId(station.getId());
        dto.setLatitude(station.getLatitude());
        dto.setLongitude(station.getLongitude());
        return dto;
    }
}

