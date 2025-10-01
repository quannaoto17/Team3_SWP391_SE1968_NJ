package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
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
    private int productID;
    private String name;
    private String brand;
    private String model;
    private int wattage; // in Watts
    private String efficiencyRating; // e.g., 80 Plus Bronze, 80 Plus Gold
    private boolean modular; // whether the PSU is modular

    @OneToOne
    @MapsId
    @JoinColumn(name = "productID")
    private Product product;
}
