package com.alek0m0m.dronepizzabackend.domains.station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    @Query("SELECT s FROM Station s ORDER BY (SELECT COUNT(d) FROM Drone d WHERE d.station = s) ASC")
    List<Station> findAllOrderByDroneCount();
    
    // Find stations within a certain radius of a point
    @Query("SELECT s FROM Station s WHERE " +
           "acos(sin(:lat) * sin(s.latitude) + cos(:lat) * cos(s.latitude) * cos(s.longitude - :lon)) * 6371 <= :radius")
    List<Station> findStationsWithinRadius(
        @Param("lat") double latitude,
        @Param("lon") double longitude,
        @Param("radius") double radiusInKm
    );
}

