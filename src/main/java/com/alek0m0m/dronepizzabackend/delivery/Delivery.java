package com.alek0m0m.dronepizzabackend.delivery;


import com.alek0m0m.dronepizzabackend.drone.Drone;
import com.alek0m0m.dronepizzabackend.pizza.Pizza;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime expectedDeliveryTime;

    @Column
    private LocalDateTime actualDeliveryTime;

    @Column(nullable = false)
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;


}