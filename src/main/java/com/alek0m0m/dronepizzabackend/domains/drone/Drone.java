package com.alek0m0m.dronepizzabackend.domains.drone;

import com.alek0m0m.dronepizzabackend.domains.delivery.Delivery;
import com.alek0m0m.dronepizzabackend.domains.station.Station;
import jakarta.persistence.*;
import lombok.ToString;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@Entity
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DroneStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @OneToMany(mappedBy = "drone")
    private List<Delivery> deliveries = new ArrayList<>();

    public enum DroneStatus {
        I_DRIFT,        // in operation
        UDE_AF_DRIFT,   // out of operation
        UDFASET        // retired
    }


    // ----------------- Getters/Setters -----------------

    // Getters, setters, constructors

    public Long getId() {
        return id;
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public DroneStatus getStatus() {
        return status;
    }

    public Station getStation() {
        return station;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

        private String getDeliveriesIds() {

            StringBuilder sb = new StringBuilder();
            for (Delivery delivery : deliveries) {
                sb.append(delivery.getId()).append(", ");
            }
            return sb.toString();
        }

    public Drone setId(Long id) {
        this.id = id;
        return this;
    }

    public Drone setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public Drone setStatus(DroneStatus status) {
        this.status = status;
        return this;
    }

    public Drone setStation(Station station) {
        this.station = station;
        if (station != null && !station.getDrones().contains(this)) {
            station.addDrone(this);
        }
        return this;
    }

    public Drone setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
        return this;
    }



    // ----------------- helper operations -----------------
    // toString, equals and hashCode

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", serialNumber=" + serialNumber +
                ", status=" + status +
                ", station=" + (station != null ? station.getId() : null) +
                //", deliveries=" + (deliveries != null ? getDeliveriesIds() : null) +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Drone drone = (Drone) o;
        // Compare by ID, if both entities are persisted
        if (id != null && drone.id != null) {
            return id.equals(drone.id);
        }

        // Compare by serialNumber (UUID), since unique and required
        return serialNumber != null && serialNumber.equals(drone.serialNumber);
    }

    @Override
    public int hashCode() {
        // Use ID if available, otherwise use serialNumber
        return id != null ? id.hashCode()
                : serialNumber != null
                ? serialNumber.hashCode() : super.hashCode();
    }


}