package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
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
    private int productID;
    private String name;
    private String model;
    private int vram; // in GB
    private double baseClock; // in GHz
    private double boostClock; // in GHz
    private int tdp; // in Watts

    @OneToOne
    @MapsId
    @JoinColumn(name = "productID")
    private Product product;
}
