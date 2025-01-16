package com.alek0m0m.dronepizzabackend.drone;

import com.alek0m0m.dronepizzabackend.station.StationDTO;
import java.util.Objects;
import java.util.UUID;

public class DroneDTO {
    private UUID serialNumber;
    private String status;
    private StationDTO station;

    public DroneDTO(UUID serialNumber, String status, StationDTO station) {
        this.serialNumber = serialNumber;
        this.status = status;
        this.station = station;
    }

    public DroneDTO() {
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public DroneDTO setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DroneDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public StationDTO getStation() {
        return station;
    }

    public DroneDTO setStation(StationDTO station) {
        this.station = station;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroneDTO droneDTO = (DroneDTO) o;
        return Objects.equals(serialNumber, droneDTO.serialNumber) &&
                Objects.equals(status, droneDTO.status) &&
                Objects.equals(station, droneDTO.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, status, station);
    }

    @Override
    public String toString() {
        return "DroneDTO{" +
                "serialNumber=" + serialNumber +
                ", status='" + status + '\'' +
                ", station=" + station +
                '}';
    }
}