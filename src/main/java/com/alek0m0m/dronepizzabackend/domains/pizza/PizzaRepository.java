package com.alek0m0m.dronepizzabackend.domains.pizza;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// PizzaRepository.java
@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    List<Pizza> findByPriceInDKKLessThanEqual(Integer maxPrice);

    Optional<Pizza> findByTitleIgnoreCase(String title);

    @Query("SELECT p FROM Pizza p ORDER BY SIZE(p.deliveries) DESC")
    List<Pizza> findAllOrderByPopularity();
}
