package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
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
    @Column(name = "productID")
    private int productId;
    private String name;
    private String brand;
    private String model;
    private String type; // e.g., Air, Liquid
    private int fanSize; // in mm
    private int noiseLevel; // in dBA

    @OneToOne
    @MapsId
    @JoinColumn(name = "productID")
    private Product product;
}
