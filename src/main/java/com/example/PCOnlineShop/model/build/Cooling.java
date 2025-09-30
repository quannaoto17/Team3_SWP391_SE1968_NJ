package com.example.PCOnlineShop.model.build;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cooling")

public class Cooling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String model;
    private String type; // e.g., Air, Liquid
    private int fanSize; // in mm
    private int noiseLevel; // in dBA
    private double price; // in USD
}
