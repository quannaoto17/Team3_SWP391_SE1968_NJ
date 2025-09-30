package com.example.PCOnlineShop.model.build;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "gpu")

public class GPU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String model;
    private int vram; // in GB
    private double baseClock; // in GHz
    private double boostClock; // in GHz
    private int tdp; // in Watts
    private double price; // in USD
}
