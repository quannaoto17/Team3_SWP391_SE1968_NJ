package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
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
    private int productId;
    private String name;
    private String brand;
    private String model;
    private int capacity; // in GB or TB
    private String type; // e.g., SSD, HDD, NVMe
    private String interfaceType; // e.g., SATA, PCIe

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;
}
