package com.alek0m0m.dronepizzabackend.station;

import java.util.Objects;

public class StationDTO {
    private Long id;
    private Double latitude;
    private Double longitude;

    public Long getId() {
        return id;
    }

    public StationDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public StationDTO setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public StationDTO setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationDTO that = (StationDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude);
    }

    @Override
    public String toString() {
        return "StationDTO{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}