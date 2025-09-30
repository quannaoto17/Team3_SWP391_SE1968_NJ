package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cpu")

public class CPU {
    @Id
    private int productId;
    private String name;
    private String brand;
    private String model;
    private int cores;
    private int threads;
    private double baseClock; // in GHz
    private double boostClock; // in GHz
    private String socketType;
    private int tdp; // in Watts

    @OneToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product;
}
