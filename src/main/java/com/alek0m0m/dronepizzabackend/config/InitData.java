package com.alek0m0m.dronepizzabackend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alek0m0m.dronepizzabackend.domains.pizza.*;
import com.alek0m0m.dronepizzabackend.domains.station.*;
import com.alek0m0m.dronepizzabackend.domains.delivery.*;
import com.alek0m0m.dronepizzabackend.domains.drone.*;



@Component
public class InitData {

    private final StationRepository stationRepository;
    private final PizzaRepository pizzaRepository;

    @Autowired
    public InitData(StationRepository stationRepository, PizzaRepository pizzaRepository) {
        this.stationRepository = stationRepository;
        this.pizzaRepository = pizzaRepository;
    }


    @PostConstruct
    public void init() {
        initializeStations();
        initializePizzas();
    }

    private void initializeStations() {
        if (stationRepository.count() == 0) {
            // Create three stations around Copenhagen center (55.41N 12.34E)
            Station station1 = new Station();
            station1.setLatitude(55.41);
            station1.setLongitude(12.34);
            stationRepository.save(station1);

            Station station2 = new Station();
            station2.setLatitude(55.43);  // Slightly north
            station2.setLongitude(12.33);  // Slightly west
            stationRepository.save(station2);

            Station station3 = new Station();
            station3.setLatitude(55.40);  // Slightly south
            station3.setLongitude(12.35);  // Slightly east
            stationRepository.save(station3);
        }
    }

    private void initializePizzas() {
        if (pizzaRepository.count() == 0) {
            Pizza margherita = new Pizza();
            margherita.setTitle("Margherita");
            margherita.setPriceInDKK(85);
            pizzaRepository.save(margherita);

            Pizza pepperoni = new Pizza();
            pepperoni.setTitle("Pepperoni");
            pepperoni.setPriceInDKK(95);
            pizzaRepository.save(pepperoni);

            Pizza hawaii = new Pizza();
            hawaii.setTitle("Hawaii");
            hawaii.setPriceInDKK(90);
            pizzaRepository.save(hawaii);

            Pizza vesuvio = new Pizza();
            vesuvio.setTitle("Vesuvio");
            vesuvio.setPriceInDKK(89);
            pizzaRepository.save(vesuvio);

            Pizza calzone = new Pizza();
            calzone.setTitle("Calzone");
            calzone.setPriceInDKK(99);
            pizzaRepository.save(calzone);
        }
    }


}
