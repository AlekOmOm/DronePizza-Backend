package com.alek0m0m.dronepizzabackend.drone;

import com.alek0m0m.dronepizzabackend.station.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    Optional<Drone> findBySerialNumber(UUID serialNumber);

    List<Drone> findByStatus(Drone.DroneStatus status);

    List<Drone> findByStation(Station station);

    @Query("SELECT d.station, COUNT(d) as droneCount FROM Drone d GROUP BY d.station ORDER BY droneCount ASC")
    List<Object[]> findStationsOrderedByDroneCount();

    boolean existsBySerialNumber(UUID serialNumber);
}
