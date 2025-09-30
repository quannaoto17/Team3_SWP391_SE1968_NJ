package com.example.PCOnlineShop.model.build;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "powersupply")

public class PowerSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String model;
    private int wattage; // in Watts
    private String efficiencyRating; // e.g., 80 Plus Bronze, 80 Plus Gold
    private boolean modular; // whether the PSU is modular
    private double price; // in USD
}
