package com.alek0m0m.dronepizzabackend.domains.pizza;

import java.util.Objects;

public class PizzaDTO {
    private Long id;
    private String title;
    private Integer priceInDKK;

    public Long getId() {
        return id;
    }

    public PizzaDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PizzaDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getPriceInDKK() {
        return priceInDKK;
    }

    public PizzaDTO setPriceInDKK(Integer priceInDKK) {
        this.priceInDKK = priceInDKK;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaDTO pizzaDTO = (PizzaDTO) o;
        return Objects.equals(id, pizzaDTO.id) &&
                Objects.equals(title, pizzaDTO.title) &&
                Objects.equals(priceInDKK, pizzaDTO.priceInDKK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, priceInDKK);
    }

    @Override
    public String toString() {
        return "PizzaDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", priceInDKK=" + priceInDKK +
                '}';
    }
}