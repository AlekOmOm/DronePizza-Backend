package com.alek0m0m.dronepizzabackend.drone;

import com.alek0m0m.dronepizzabackend.delivery.Delivery;
import com.alek0m0m.dronepizzabackend.station.Station;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}