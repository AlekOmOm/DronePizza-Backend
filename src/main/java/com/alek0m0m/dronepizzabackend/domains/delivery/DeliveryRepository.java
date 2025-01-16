package com.alek0m0m.dronepizzabackend.domains.delivery;

import com.alek0m0m.dronepizzabackend.domains.drone.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// DeliveryRepository.java
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    // Find undelivered orders (no actual delivery time)
    List<Delivery> findByActualDeliveryTimeIsNullOrderByExpectedDeliveryTimeAsc();

    // Find deliveries without assigned drone
    List<Delivery> findByDroneIsNullOrderByExpectedDeliveryTimeAsc();

    // Find active deliveries for a specific drone
    List<Delivery> findByDroneAndActualDeliveryTimeIsNull(Drone drone);

    // Find deliveries expected within time window
    List<Delivery> findByExpectedDeliveryTimeBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    // Count unfinished deliveries for a drone
    @Query("SELECT COUNT(d) FROM Delivery d WHERE d.drone = :drone AND d.actualDeliveryTime IS NULL")
    long countUnfinishedDeliveriesForDrone(@Param("drone") Drone drone);

    // Find overdue undelivered orders
    @Query("SELECT d FROM Delivery d WHERE d.actualDeliveryTime IS NULL AND d.expectedDeliveryTime < :now")
    List<Delivery> findOverdueDeliveries(@Param("now") LocalDateTime now);
}
