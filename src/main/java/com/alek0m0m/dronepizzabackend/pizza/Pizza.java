package com.alek0m0m.dronepizzabackend.pizza;


import com.alek0m0m.dronepizzabackend.delivery.Delivery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer priceInDKK;

    @OneToMany(mappedBy = "pizza")
    private List<Delivery> deliveries = new ArrayList<>();

    // Getters, setters, constructors
}