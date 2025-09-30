package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "memory")

public class Memory {
    @Id
    private int productId;
    private String name;
    private String brand;
    private String model;
    private int capacity; // in GB
    private int speed; // in MHz
    private String type; // e.g., DDR4, DDR5
    private int modules; // number of modules (e.g., 2 for a 2x8GB kit)

    @OneToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product;
}
