package com.alek0m0m.dronepizzabackend.domains.station;

import com.alek0m0m.dronepizzabackend.domains.drone.Drone;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @OneToMany(mappedBy = "station", fetch = FetchType.EAGER)
    private List<Drone> drones = new ArrayList<>();


    // drones operations

    public Station addDrone(Drone drone) {
        if (drone != null && !drones.contains(drone)) {
            drones.add(drone);
            if (drone.getStation() != this) {
                drone.setStation(this);
            }
        }
        return this;
    }

    public Station removeDrone(Drone drone) {
        if (drone != null) {
            drones.remove(drone);
            drone.setStation(null);
        }
        return this;
    }

    // Getters, setters, constructors

    public Long getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public Station setLatitude(Double latitude) {
        if (latitude != null) {
            this.latitude = latitude;
        }
        return this;
    }

    public Station setLongitude(Double longitude) {
        if (longitude != null) {
            this.longitude = longitude;
        }
        return this;
    }

    public Station setId(Long id) {
        if (id != null) {
            this.id = id;
        }
        return this;
    }

    public Station setDrones(List<Drone> drones) {
        if (drones != null) {
            this.drones = drones;
        }
        return this;
    }

    // Constructors
    public Station() {
    }

    public Station(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Station(Long id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // tostring

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", drones=" + drones +
                '}';
    }


}
