package com.example.PCOnlineShop.model.build;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "storage")

public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String model;
    private int capacity; // in GB or TB
    private String type; // e.g., SSD, HDD, NVMe
    private String interfaceType; // e.g., SATA, PCIe
    private double price; // in USD
}
